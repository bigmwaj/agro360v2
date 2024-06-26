package com.agro360.service.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.PaiementParamBean;
import com.agro360.bo.bean.av.ReglementBean;
import com.agro360.bo.bean.av.ReglementCommandeBean;
import com.agro360.bo.bean.av.ReglementFactureBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.CommandeOperation;
import com.agro360.operation.logic.av.FactureOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.av.ReglementCommandeOperation;
import com.agro360.operation.logic.av.ReglementFactureOperation;
import com.agro360.operation.logic.finance.TransactionOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.service.common.UserInput;
import com.agro360.service.stock.InventaireServiceHelper;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

@Transactional(rollbackFor = Exception.class)
@Service
public class CommandeService extends AbstractService {

	@Autowired
	private CommandeOperation operation;
	
	@Autowired
	private TransactionOperation transactionOperation;
	
	@Autowired
	private ReglementCommandeOperation reglementCommandeOperation ;
	
	@Autowired
	private ReglementFactureOperation reglementFactureOperation ;
	
	@Autowired
	private FactureOperation factureOperation;
	
	@Autowired
	private LigneOperation ligneOperation;
	
	@Autowired
	private InventaireServiceHelper inventaireServiceHelper;
	
	@Autowired
	private LigneServiceHelper ligneServiceHelper;
	
	public List<CommandeBean> search(ClientContext ctx, CommandeSearchBean searchBean) {
		return operation.findCommandesByCriteria(ctx, searchBean);
	}
	
	@UserInput(metadataBeanName = "av/commande", validatorBeanName = "")
	public void save(ClientContext ctx, CommandeBean bean) {
		switch (bean.getAction()) {
		case CREATE:
			create(ctx, bean);
			break;
			
		case UPDATE:
			update(ctx, bean);
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case AANN:
				preAnnulerReglements(ctx, bean);
				operation.demanderAnnulationCommande(ctx, bean);
				break;
				
			case ANNL:
				operation.annulerCommande(ctx, bean);
				break;
				
			case ATAP:
				terminerCommande(ctx, bean);			
				break;
				
			case TERM:
				operation.terminerCommande(ctx, bean);
				break;
				
			case CLOT:
				operation.cloturerCommande(ctx, bean);
				break;

			default:
				break;
			}
			break;

		case DELETE:
			ligneOperation.deleteLignesCommande(ctx, bean);
			operation.deleteCommande(ctx, bean);
			break;
			
		default:
			
		}
	}
	public CommandeBean findCommande(ClientContext ctx, String commandeCode) {
		return operation.findCommandeByCode(ctx, commandeCode);
	}

	public List<ReglementBean> findReglements(ClientContext ctx, String commandeCode) {
		var reglements = reglementCommandeOperation.findReglementsCommande(ctx, commandeCode)
				.stream().map(ReglementCommandeBean::getTransaction)
				.map(ReglementBean::new)
				.collect(Collectors.toCollection(ArrayList<ReglementBean>::new));
		
		var factures = factureOperation.findFacturesByCommandeCode(ctx, commandeCode)
				.stream()
				.map(ReglementBean::new)
				.collect(Collectors.toList());
		
		reglements.addAll(factures);
		return reglements;
		
	}
	
	public void reglerCommande(ClientContext ctx, String commandeCode, List<PaiementParamBean> paiements) {
		final var bean = operation.findCommandeByCode(ctx, commandeCode);
		var status = bean.getStatus().getValue();
		var prixTotal = bean.getPrixTotal().getValue();
		
		Predicate<PaiementParamBean> nonNull = e -> e.getMontant().getValue() != null;
		Predicate<PaiementParamBean> positif = e -> BigDecimal.ZERO.compareTo(e.getMontant().getValue()) < 0 ;
		
		var paiementsValides = paiements.stream()
			.filter(nonNull)
			.filter(positif)
			.collect(Collectors.toList());
		
		var paiementTotal = paiementsValides.stream()
			.map(PaiementParamBean::getMontant)
			.map(FieldMetadata::getValue)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
		
		var cumulPaiement = calculerCumulPaiements(ctx, bean);
		
		var nouveauCumulPaiement = paiementTotal.add(cumulPaiement);
		
		if( nouveauCumulPaiement.compareTo(prixTotal) > 0 ) {
			var msgTpl = "Trop perçu %.2f de la commande. Montant total: %.2f, Cumul Paiement %.2f, Paiement demandé: %.2f";
			throw new RuntimeException(String.format(msgTpl, nouveauCumulPaiement.subtract(prixTotal), prixTotal, cumulPaiement, paiementTotal));
		}
		
		// On enregistre les nouveaux règlements
		reglerCommande(ctx, bean, paiementsValides);
		
		if( prixTotal.compareTo(nouveauCumulPaiement) == 0 ) {
			// On termine la commande
			terminerCommande(ctx, bean);
		}else if( !CommandeStatusEnumVd.RGLM.equals(status)) {// Paiement du complément
			initierPaiementCommande(ctx, bean);
		}
		
		bean.getCumulPaiement().setValue(nouveauCumulPaiement);
		bean.getCumulPaiement().setValueChanged(true);
		operation.updateCommande(ctx, bean);
	}
	
	private TransactionBean setToApprouvee(TransactionBean bean) {
		bean.getStatus().setValue(TransactionStatusEnumVd.APPROUVEE);
		return bean;
	}
	
	private void approuverReglements(ClientContext ctx, CommandeBean bean) {
		var commandeCode = bean.getCommandeCode().getValue();
		
		Predicate<TransactionBean> isReservee;
		isReservee = e -> TransactionStatusEnumVd.RESERVEE.equals(e.getStatus().getValue());
		
		Consumer<TransactionBean> approuverFn;
		approuverFn = e -> transactionOperation.approuverTransaction(ctx, e);
		
		reglementCommandeOperation.findReglementsCommande(ctx, commandeCode)
			.stream()
			.map(ReglementCommandeBean::getTransaction)
			.filter(isReservee)
			.map(this::setToApprouvee)
			.forEach(approuverFn);
	}
	
	private boolean existReglementsAnnules(ClientContext ctx, CommandeBean bean) {
		var commandeCode = bean.getCommandeCode().getValue();
		
		return reglementCommandeOperation.findReglementsCommande(ctx, commandeCode)
			.stream()
			.map(ReglementCommandeBean::getTransaction)
			.map(TransactionBean::getStatus)
			.map(FieldMetadata::getValue)
			.anyMatch(TransactionStatusEnumVd.ANNULEE::equals);
	}

	private TransactionBean setToAnnulee(TransactionBean bean) {
		bean.getStatus().setValue(TransactionStatusEnumVd.ANNULEE);
		return bean;
	}
	
	private void preAnnulerReglements(ClientContext ctx, CommandeBean bean) {
		var commandeCode = bean.getCommandeCode().getValue();
		
		Predicate<TransactionBean> isReservee;
		isReservee = e -> TransactionStatusEnumVd.RESERVEE.equals(e.getStatus().getValue());
		
		Consumer<TransactionBean> annulerFn;
		annulerFn = e -> transactionOperation.annulerTransaction(ctx, e);
		
		reglementCommandeOperation.findReglementsCommande(ctx, commandeCode)
			.stream()
			.map(ReglementCommandeBean::getTransaction)
			.filter(isReservee)
			.map(this::setToAnnulee)
			.forEach(annulerFn);
		
		bean = operation.findCommandeByCode(ctx, commandeCode);
		bean.getCumulPaiement().setValue(BigDecimal.ZERO);
		bean.getCumulPaiement().setValueChanged(true);
		operation.updateCommande(ctx, bean);
	}

	private BigDecimal calculerCumulPaiements(ClientContext ctx, CommandeBean bean) {
		var commandeCode = bean.getCommandeCode().getValue();
		Predicate<TransactionBean> nonAnnulee = t -> !TransactionStatusEnumVd.ANNULEE.equals(t.getStatus().getValue());
		return reglementCommandeOperation.findReglementsCommande(ctx, commandeCode)
			.stream()
			.map(ReglementCommandeBean::getTransaction)
			.filter(nonAnnulee)
			.map(TransactionBean::getMontant)
			.map(FieldMetadata::getValue)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
	}
	
	private void create(ClientContext ctx, CommandeBean bean) {
		initChampsCalcules(ctx, bean);
		operation.createCommande(ctx, bean);
		ligneServiceHelper.createLignesCommande(ctx, bean, bean.getLignes());
	}
	
	private void update(ClientContext ctx, CommandeBean bean) {		
		initChampsCalcules(ctx, bean);
		operation.updateCommande(ctx, bean);
		ligneServiceHelper.syncLignesCommande(ctx, bean, bean.getLignes());
	}
	
	
	private void reglerCommande(ClientContext ctx, CommandeBean bean, List<PaiementParamBean> paiements) {
		paiements.stream().forEach(e -> reglerCommande(ctx, bean, e));
	}

	private void initierPaiementCommande(ClientContext ctx, CommandeBean bean) {
		bean.getStatus().setValue(CommandeStatusEnumVd.RGLM);
		bean.getStatusDate().setValue(LocalDateTime.now());
		operation.initPaiementCommande(ctx, bean);
	}

	private void terminerCommande(ClientContext ctx, CommandeBean bean) {		
		var cumulPaiements = calculerCumulPaiements(ctx, bean);
		var freshBean = operation.findCommandeByCode(ctx, bean.getCommandeCode().getValue());
		var prixTotalNet = freshBean.getPrixTotal().getValue();
		var reste = prixTotalNet.subtract(cumulPaiements);
		
		approuverReglements(ctx, bean);
		
		var anyAnnulation = existReglementsAnnules(ctx, bean);
		if( anyAnnulation || reste.compareTo(BigDecimal.ZERO) != 0 ) {
			bean.getStatus().setValue(CommandeStatusEnumVd.ATAP);
			bean.getStatusDate().setValue(LocalDateTime.now());
			operation.demanderApprobationCommande(ctx, bean);
		}else {
			bean.getStatus().setValue(CommandeStatusEnumVd.SOLD);
			bean.getStatusDate().setValue(LocalDateTime.now());
			operation.terminerCommande(ctx, bean);					
		}

		if( reste.compareTo(BigDecimal.ZERO) != 0 ) {
			if( reste.compareTo(BigDecimal.ZERO) > 0 ) {
				reglerCommandeACredit(ctx, freshBean, reste);
			}else {
				var msgTpl = "Trop perçu %.2f de la commande. Montant total %.2f, Cumul Paiement %.2f";
				throw new RuntimeException(String.format(msgTpl, reste, prixTotalNet, cumulPaiements));
			}
		}
		
		if( CommandeTypeEnumVd.VENTE.equals(bean.getType().getValue()) ) {
			inventaireServiceHelper.enregistrerSortiesMagasin(ctx, bean);
		}
	}
	
	private TransactionBean initTransaction(CommandeBean bean) {
		var typeCommande = bean.getType().getValue();
		var tx = new TransactionBean();
		
		switch (typeCommande) {
		case ACHAT:
			tx.getType().setValue(TransactionTypeEnumVd.DEPENSE);
			tx.getRubrique().getRubriqueCode().setValue("ACHAT");
			break;
			
		case VENTE:
			tx.getType().setValue(TransactionTypeEnumVd.RECETTE);
			tx.getRubrique().getRubriqueCode().setValue("VENTE");
			break;

		default:
			throw new RuntimeException(String.format("Type de commande %s non prix en charge", typeCommande));
		}

		return tx;
	}
	
	private void reglerCommande(ClientContext ctx, CommandeBean bean, PaiementParamBean paiement) {
		var tx = initTransaction(bean);
		tx.getStatus().setValue(TransactionStatusEnumVd.RESERVEE);
		tx.setCompte(paiement.getCompte());
		tx.setPartner(bean.getPartner());
		
		tx.getTransactionCode().setValue(transactionOperation.generateTransactionCode());
		tx.getNote().setValue(bean.getDescription().getValue());
		tx.getDate().setValue(bean.getDate().getValue());
		tx.getMontant().setValue(paiement.getMontant().getValue());
		
		transactionOperation.createTransaction(ctx, tx);
		
		var rg = new ReglementCommandeBean(); // Joindre à la commande
		rg.setTransaction(tx);
		rg.getCommandeCode().setValue(bean.getCommandeCode().getValue());
		
		reglementCommandeOperation.createReglement(ctx, rg);
	}
	
	private FactureBean initFacture(CommandeBean bean) {
		var typeCommande = bean.getType().getValue();
		var facture = new FactureBean();
		
		switch (typeCommande) {
		case ACHAT:
			facture.getType().setValue(FactureTypeEnumVd.ACHAT);
			break;
			
		case VENTE:
			facture.getType().setValue(FactureTypeEnumVd.VENTE);
			break;

		default:
			throw new RuntimeException(String.format("Type de commande %s non prix en charge", typeCommande));
		}
		
		return facture;
	}

	private void assignerReglementsCommandeDansFacture(ClientContext ctx, CommandeBean bean, FactureBean facture) {
		var commandeCode = bean.getCommandeCode().getValue();
		
		Predicate<TransactionBean> isAppr;
		isAppr = e -> TransactionStatusEnumVd.APPROUVEE.equals(e.getStatus().getValue());
		
		Function<TransactionBean, ReglementFactureBean> mapper;
		mapper = e -> new ReglementFactureBean(e, facture.getFactureCode().getValue());
		
		Consumer<ReglementFactureBean> save;
		save = e -> reglementFactureOperation.createReglement(ctx, e);
		
		reglementCommandeOperation.findReglementsCommande(ctx, commandeCode)
			.stream()
			.map(ReglementCommandeBean::getTransaction)
			.filter(isAppr)
			.map(mapper)
			.forEach(save);
	}

	private void reglerCommandeACredit(ClientContext ctx, CommandeBean bean, BigDecimal reste) {
		
		var facture = initFacture(bean);
		
		facture.getFactureCode().setValue(factureOperation.generateFactureCode());
		facture.setCommande(bean);
		facture.setPartner(bean.getPartner());
		facture.getStatus().setValue(FactureStatusEnumVd.TERM);
		facture.getStatusDate().setValue(LocalDateTime.now());
		
		facture.getDate().setValue(bean.getDate().getValue());
		facture.getDescription().setValue(bean.getDescription().getValue());
		facture.getPrixTotal().setValue(bean.getPrixTotal().getValue());
		facture.getPrixTotalHT().setValue(bean.getPrixTotalHT().getValue());
		facture.getTaxe().setValue(bean.getTaxe().getValue());
		facture.getCumulPaiement().setValue(bean.getCumulPaiement().getValue());
		facture.getRemise().setValue(bean.getRemise().getValue());

		factureOperation.createFacture(ctx, facture);
		
		assignerReglementsCommandeDansFacture(ctx, bean, facture);
		ligneServiceHelper.assignerFactureTaxesAppliquees(ctx, bean, facture);
	}

	private BigDecimal cumulerPrix(List<LigneBean> lignes, Function<LigneBean, FieldMetadata<BigDecimal>> mapper) {
		return lignes.stream()
    	.map(mapper)
    	.map(FieldMetadata::getValue)
    	.map(BigDecimal.class::cast)
    	.reduce(BigDecimal::add)
		.orElse(BigDecimal.ZERO);
	}
	
	private void initChampsCalcules(ClientContext ctx, CommandeBean bean) {
		Predicate<LigneBean> deleted = e -> ClientOperationEnumVd.DELETE.equals(e.getAction());
		
		bean.getLignes().stream()
			.filter(Predicate.not(deleted))
			.forEach(e -> ligneServiceHelper.initChampsCalcules(ctx, bean, e));
        
        var prixTotalHT = cumulerPrix(bean.getLignes(), LigneBean::getPrixTotalHT);
        var taxe = cumulerPrix(bean.getLignes(), LigneBean::getTaxe);
        var pritTotal = cumulerPrix(bean.getLignes(), LigneBean::getPrixTotal);
        var remise = cumulerPrix(bean.getLignes(), LigneBean::getRemise);
        
        bean.getPrixTotalHT().setValue(prixTotalHT);
        bean.getRemise().setValue(remise);
        bean.getTaxe().setValue(taxe);
        bean.getPrixTotal().setValue(pritTotal);
        
        bean.getPrixTotalHT().setValueChanged(true);
        bean.getRemise().setValueChanged(true);
        bean.getTaxe().setValueChanged(true);
        bean.getPrixTotal().setValueChanged(true);
	}
}
