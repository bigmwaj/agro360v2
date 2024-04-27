package com.agro360.service.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import com.agro360.bo.bean.av.LigneTaxeBean;
import com.agro360.bo.bean.av.ReglementCommandeBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.CommandeOperation;
import com.agro360.operation.logic.av.FactureOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.av.ReglementCommandeOperation;
import com.agro360.operation.logic.finance.TransactionOperation;
import com.agro360.operation.logic.stock.ArticleTaxeOperation;
import com.agro360.service.common.AbstractService;
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
	private FactureOperation factureOperation;
	
	@Autowired
	private LigneOperation ligneOperation;
	
	@Autowired
	private ArticleTaxeOperation articleTaxeOperation;
	
	@Autowired
	private InventaireServiceHelper inventaireServiceHelper;
	
	@Autowired
	private LigneServiceHelper ligneServiceHelper;
	
	public List<CommandeBean> search(ClientContext ctx, CommandeSearchBean searchBean) {
		return operation.findCommandesByCriteria(ctx, searchBean);
	}
	
	private BigDecimal cumulerPrix(List<LigneBean> lignes, Function<LigneBean, FieldMetadata<BigDecimal>> mapper) {
		return lignes.stream()
    	.map(mapper)
    	.map(FieldMetadata::getValue)
    	.map(BigDecimal.class::cast)
    	.reduce(BigDecimal::add)
		.orElse(BigDecimal.ZERO);
	}
	
	private void initPrix(ClientContext ctx, CommandeBean bean) {
		Predicate<LigneBean> notDeleted = e -> !ClientOperationEnumVd.DELETE.equals(e.getAction());
		
		bean.getLignes().stream()
			.filter(notDeleted)
			.forEach(e -> ligneServiceHelper.initPrix(ctx, e));
        
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
			case ANNL:
				operation.annulerCommande(ctx, bean);
				break;
				
			case APPR:
				var cumulPaiements = calculerCumulPaiements(ctx, bean);
				var freshBean = operation.findCommandeByCode(ctx, bean.getCommandeCode().getValue());
				var prixTotalNet = freshBean.getPrixTotal().getValue();
				var reste = prixTotalNet.subtract(cumulPaiements);
				if( reste.compareTo(BigDecimal.ZERO) > 0 ) {
					reglerCommandeACredit(ctx, freshBean, reste);
				}else {
					throw new RuntimeException("Trop perçu de la commande!");
				}
				approuverReglements(ctx, bean);
				operation.approuverCommande(ctx, bean);
				
				if( CommandeTypeEnumVd.VENTE.equals(bean.getType().getValue()) ) {
					inventaireServiceHelper.enregistrerSortiesMagasin(ctx, bean);
				}
				
				break;
				
			case ATAP:
				operation.demanderApprobationCommande(ctx, bean);
				
				break;
				
			case RECP:
				operation.receptionnerPartCommande(ctx, bean);
				break;
				
			case REJT:
				operation.rejeterCommande(ctx, bean);
				break;
				
			case RECT:
				operation.receptionnerCommande(ctx, bean);
				break;

			default:
				break;
			}
			break;

		case DELETE:
			
			deleteLignesCommande(ctx, bean);
			operation.deleteCommande(ctx, bean);
			break;
			
		default:
			
		}
	}
	
	private void approuverReglements(ClientContext ctx, CommandeBean bean) {
		var commandeCode = bean.getCommandeCode().getValue();
		var regs = reglementCommandeOperation.findReglementsByCommandeCode(ctx, commandeCode);
		
		// TODO Approuver les transactions
	}

	private BigDecimal calculerCumulPaiements(ClientContext ctx, CommandeBean bean) {
		var commandeCode = bean.getCommandeCode().getValue();
		var regs = reglementCommandeOperation.findReglementsByCommandeCode(ctx, commandeCode);
		return regs.stream()
			.map(ReglementCommandeBean::getTransaction)
			.map(TransactionBean::getMontant)
			.map(FieldMetadata::getValue)
			.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}
	
	private void create(ClientContext ctx, CommandeBean bean) {
		var paiement = bean.getPaiementComptant().getValue();
		if( paiement == null ) {
			paiement = BigDecimal.ZERO;
		}
		
		bean.getCumulPaiement().setValue(paiement);
		
		initPrix(ctx, bean);
		operation.createCommande(ctx, bean);
		createLignesCommande(ctx, bean);
	}
	
	private void update(ClientContext ctx, CommandeBean bean) {
		var paiement = bean.getPaiementComptant().getValue();
		if( paiement == null ) {
			paiement = BigDecimal.ZERO;
		}
		var cumulEnCours = calculerCumulPaiements(ctx, bean);
		cumulEnCours = cumulEnCours.add(paiement);
		
		bean.getCumulPaiement().setValueChanged(true);
		bean.getCumulPaiement().setValue(cumulEnCours);
		
		getLogger().debug("Le cumul en cours est {}", cumulEnCours);
		
		initPrix(ctx, bean);
		operation.updateCommande(ctx, bean);
		syncLignesCommande(ctx, bean);
	}
	
	public void encaisser(ClientContext ctx, CommandeBean bean) {
		switch (bean.getAction()) {
		case CREATE:
			create(ctx, bean);
			break;
			
		case UPDATE:
			var status = bean.getStatus().getValue();
			if( CommandeStatusEnumVd.BRLN.equals(status) || CommandeStatusEnumVd.PAYT.equals(status)) {
				update(ctx, bean);
			}
			break;
			
		default:
			
		}
		var status = bean.getStatus().getValue();
		if( !CommandeStatusEnumVd.PAYT.equals(status)) {
			bean.getStatus().setValue(CommandeStatusEnumVd.PAYT);
			operation.initPaiementCommande(ctx, bean);
		}
		
		encaisserPaiement(ctx, bean);
	}
	
	private void encaisserPaiement(ClientContext ctx, CommandeBean bean) {
		var montantPaye = bean.getPaiementComptant().getValue();
		if( !Objects.isNull(montantPaye) && !BigDecimal.ZERO.equals(montantPaye) ) {
			reglerCommandeComptant(ctx, bean);
		}
	}
	
	private void reglerCommandeComptant(ClientContext ctx, CommandeBean bean) {
		var tx = new TransactionBean();
		
		var rubriqueCode = "VENTE";
		var compteCode = bean.getCompte().getCompteCode().getValue();
		
		if( compteCode.startsWith("CLIENT/")) {
			tx.getType().setValue(TransactionTypeEnumVd.RETRAIT);
		}else {
			tx.getType().setValue(TransactionTypeEnumVd.RECETTE);
		}
		tx.getRubrique().getRubriqueCode().setValue(rubriqueCode);
		
		tx.setCompte(bean.getCompte());
		tx.setPartner(bean.getPartner());
		
		tx.getTransactionCode().setValue(transactionOperation.generateTransactionCode());
		tx.getStatus().setValue(TransactionStatusEnumVd.RESERVEE);
		tx.getNote().setValue(bean.getDescription().getValue());
		tx.getDate().setValue(bean.getDate().getValue());
		tx.getMontant().setValue(bean.getPaiementComptant().getValue());
		
		transactionOperation.createTransaction(ctx, tx);
		
		var rg = new ReglementCommandeBean(); // Joindre à la commande
		rg.setTransaction(tx);
		rg.getCommandeCode().setValue(bean.getCommandeCode().getValue());
		
		reglementCommandeOperation.createReglement(ctx, rg);
	}

	private void reglerCommandeACredit(ClientContext clientContext, CommandeBean bean, BigDecimal reste) {
		var facture = new FactureBean();
		facture.getFactureCode().setValue(factureOperation.generateFactureCode());
		facture.setCommande(bean);
		facture.setPartner(bean.getPartner());
		facture.getStatus().setValue(FactureStatusEnumVd.APPR);
		facture.getStatusDate().setValue(LocalDateTime.now());
		facture.getDate().setValue(bean.getDate().getValue());
		facture.getType().setValue(FactureTypeEnumVd.VENTE);
		facture.getMontant().setValue(reste);
		facture.getDescription().setValue(bean.getDescription().getValue());

		factureOperation.createFacture(clientContext, facture);
	}
	
	private void createLignesCommande(ClientContext ctx, CommandeBean bean) {
		for (LigneBean ligne : bean.getLignes()) {
			ligneOperation.createLigne(ctx, bean, ligne);
		}
	}
	
	private void syncLignesCommande(ClientContext ctx, CommandeBean bean) {
		for (LigneBean ligne : bean.getLignes()) {
			switch (ligne.getAction()) {
			case CREATE:
				ligneOperation.createLigne(ctx, bean, ligne);
				break;
				
			case UPDATE:
				ligneOperation.updateLigne(ctx, bean, ligne);
				break;
				
			case DELETE:
				ligneOperation.deleteLigne(ctx, bean, ligne);
				break;

			default:
				break;
			}
		}
	}
	
	private void deleteLignesCommande(ClientContext ctx, CommandeBean bean) {
		var lignes = ligneOperation.findLignesCommande(ctx, bean.getCommandeCode().getValue());
		for (LigneBean ligne : lignes) {
			ligneOperation.deleteLigne(ctx, bean, ligne);
		}
	}
	
	public List<LigneTaxeBean> findLigneTaxes(ClientContext ctx, String commandeCode, Optional<Long> ligneId,
			String articleCode) {
//		if( ligneId.filter(e -> !e.equals(0L)).isPresent()) {
//			return ligneTaxeOperation.findLigneTaxesLigne(ctx, commandeCode, ligneId.get());
//		}else {
//		}
		return articleTaxeOperation.findArticleTaxesByArticleCode(ctx, articleCode)
				.stream()
				.map(this::map)
				.collect(Collectors.toList());
	}
	
	private LigneTaxeBean map(ArticleTaxeBean bean) {
		var lt = new LigneTaxeBean();
		lt.setTaxe(bean.getTaxe());
		lt.getTaux().setValue(bean.getTaxe().getTaux().getValue());
		return lt;
	}

	public CommandeBean findCommande(ClientContext ctx, String value) {
		return operation.findCommandeByCode(ctx, value);
	}

	public LigneBean refreshLignePrix(ClientContext ctx, LigneBean ligne) {
		ligneServiceHelper.initPrix(ctx, ligne);
		return ligne;		
	}

	public List<ReglementCommandeBean> findReglementsCommande(ClientContext ctx, String commandeCode) {
		return reglementCommandeOperation.findReglementsByCommandeCode(ctx, commandeCode);
	}
}
