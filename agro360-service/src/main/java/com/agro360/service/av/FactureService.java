package com.agro360.service.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.av.EtatDetteBean;
import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.bean.av.FactureTaxeBean;
import com.agro360.bo.bean.av.PaiementParamBean;
import com.agro360.bo.bean.av.ReglementBean;
import com.agro360.bo.bean.av.ReglementFactureBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.FactureOperation;
import com.agro360.operation.logic.av.FactureTaxeOperation;
import com.agro360.operation.logic.av.ReglementFactureOperation;
import com.agro360.operation.logic.finance.TransactionOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

@Transactional(rollbackFor = Exception.class)
@Service
public class FactureService extends AbstractService {

	@Autowired
	private FactureOperation operation;
	
	@Autowired
	private FactureTaxeOperation factureTaxeOperation;
	
	@Autowired
	private ReglementFactureOperation reglementFactureOperation;
	
	@Autowired
	private TransactionOperation transactionOperation;

	public List<FactureBean> search(ClientContext ctx, FactureSearchBean searchBean) {
		return  operation.findFacturesByCriteria(ctx, searchBean);
	}

	public void save(ClientContext ctx, FactureBean bean) {
		switch (bean.getAction()) {
		case CREATE:			
			create(ctx, bean);
			
			var msgTpl = "La facture <strong>%s</strong> a été créée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;
			
		case UPDATE:
			update(ctx, bean);
			
			msgTpl = "La facture <strong>%s</strong> a été modifiée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ANNL:
				annulerFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été annulée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));				
				break;
				
			case TERM:
				approuverFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été approuvée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case ATAP:
				operation.demanderApprobationFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été envoyée en approbation avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case CLOT:
				operation.cloturerFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été cloturée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case REJT:
				operation.rejeterFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été rejetée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;

			default:
				break;
			}
			break;

		case DELETE:
			operation.deleteFacture(ctx, bean);
			
			msgTpl = "La facture <strong>%s</strong> a été supprimée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;
			
		default:
			
		}
	}

	public FactureBean findFacture(ClientContext ctx, String factureCode) {
		return operation.findFactureByCode(ctx, factureCode);
	}

	public List<ReglementBean> findReglements(ClientContext ctx, String factureCode) {
		return reglementFactureOperation.findReglementsByFactureCode(ctx, factureCode)
				.stream().map(ReglementFactureBean::getTransaction)
				.map(ReglementBean::new)
				.collect(Collectors.toList());
		
	}

	public void reglerFacture(ClientContext ctx, String factureCode, List<PaiementParamBean> paiements) {
		final var bean = operation.findFactureByCode(ctx, factureCode);
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
		
		reglerFacture(ctx, bean, paiementsValides);
		
		bean.getCumulPaiement().setValue(nouveauCumulPaiement);
		bean.getCumulPaiement().setValueChanged(true);
		operation.updateFacture(ctx, bean);
		
		var status = bean.getStatus().getValue();
		if( prixTotal.compareTo(nouveauCumulPaiement) == 0 ) {
			if( inSoldableStatus(ctx, bean) ) {
				solderFacture(ctx, bean);				
			}
		}else if( FactureStatusEnumVd.BRLN.equals(status) ){
			initierPaiementFacture(ctx, bean);
		}
	}

	public List<FactureTaxeBean> findTaxes(ClientContext ctx, String factureCode) {
		return factureTaxeOperation.findFactureTaxesFacture(ctx, factureCode);
	}
	
	public List<EtatDetteBean> genererEtatDette(ClientContext ctx) {
		return operation.genererEtatDette(ctx);
	}
	
	private BigDecimal calculerCumulPaiements(ClientContext ctx, FactureBean bean) {
		var factureCode = bean.getFactureCode().getValue();
		Predicate<TransactionBean> nonAnnulee;
		nonAnnulee = e -> !TransactionStatusEnumVd.ANNULEE.equals(e.getStatus().getValue());
		return reglementFactureOperation.findReglementsByFactureCode(ctx, factureCode)
			.stream()
			.map(ReglementFactureBean::getTransaction)
			.filter(nonAnnulee)
			.map(TransactionBean::getMontant)
			.map(FieldMetadata::getValue)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
		
	}

	private boolean inSoldableStatus(ClientContext ctx, FactureBean bean) {
		var status = bean.getStatus().getValue();
		return Arrays.asList(
				FactureStatusEnumVd.RGLM, 
				FactureStatusEnumVd.BRLN, 
				FactureStatusEnumVd.TERM)
		.stream().anyMatch(status::equals);
	}
	
	private void create(ClientContext ctx, FactureBean bean) {
		var prixTotalHT = bean.getPrixTotalHT().getValue();
		var taxe = bean.getTaxe().getValue();
		var remise = bean.getRemise().getValue();
		
		var prixTotal = prixTotalHT.add(taxe).subtract(remise);
		bean.getPrixTotal().setValue(prixTotal);
		bean.getPrixTotal().setValueChanged(true);
		
		operation.createFacture(ctx, bean);
	}
	
	private void update(ClientContext ctx, FactureBean bean) {
		var prixTotalHT = bean.getPrixTotalHT().getValue();
		var taxe = bean.getTaxe().getValue();
		var remise = bean.getRemise().getValue();
		
		var prixTotal = prixTotalHT.add(taxe).subtract(remise);
		bean.getPrixTotal().setValue(prixTotal);
		bean.getPrixTotal().setValueChanged(true);
		
		operation.updateFacture(ctx, bean);
	}
	
	private void initierPaiementFacture(ClientContext ctx, FactureBean bean) {
		bean.getStatus().setValue(FactureStatusEnumVd.RGLM);
		bean.getStatusDate().setValue(LocalDateTime.now());
		operation.initPaiementFacture(ctx, bean);
	}

	private void approuverFacture(ClientContext ctx, FactureBean bean) {
		approuverReglements(ctx, bean);
		
		bean.getStatusDate().setValue(LocalDateTime.now());
		bean.getStatus().setValue(FactureStatusEnumVd.TERM);		
		operation.annulerFacture(ctx, bean);		
	}
	
	private void approuverTransaction(ClientContext ctx, TransactionBean tx) {
		tx.getStatus().setValue(TransactionStatusEnumVd.APPROUVEE);
		tx.getStatusDate().setValue(LocalDateTime.now());
		transactionOperation.approuverTransaction(ctx, tx);
	}
	
	private void approuverReglements(ClientContext ctx, FactureBean bean) {
		var factureCode = bean.getFactureCode().getValue();
		
		Predicate<TransactionBean> reservee;
		reservee = e -> TransactionStatusEnumVd.RESERVEE.equals(e.getStatus().getValue());
		
		Consumer<TransactionBean> approuver;
		approuver = e -> approuverTransaction(ctx, e);
		
		reglementFactureOperation.findReglementsByFactureCode(ctx, factureCode)
			.stream()
			.map(ReglementFactureBean::getTransaction)
			.filter(reservee)
			.forEach(approuver);
		
	}

	private void annulerTransaction(ClientContext ctx, TransactionBean tx) {
		tx.getStatus().setValue(TransactionStatusEnumVd.ANNULEE);
		tx.getStatusDate().setValue(LocalDateTime.now());
		transactionOperation.annulerTransaction(ctx, tx);
	}
	
	private void annulerFacture(ClientContext ctx, FactureBean bean) {
		annulerReglements(ctx, bean);
		
		bean.getCumulPaiement().setValue(BigDecimal.ZERO);
		bean.getCumulPaiement().setValueChanged(true);
		operation.updateFacture(ctx, bean);
		
		bean.getStatusDate().setValue(LocalDateTime.now());
		bean.getStatus().setValue(FactureStatusEnumVd.ANNL);		
		operation.annulerFacture(ctx, bean);		
	}
	
	private void annulerReglements(ClientContext ctx, FactureBean bean) {
		var factureCode = bean.getFactureCode().getValue();
		
		Predicate<TransactionBean> reservee;
		reservee = e -> TransactionStatusEnumVd.RESERVEE.equals(e.getStatus().getValue());
		
		Consumer<TransactionBean> annuler;
		annuler = e -> annulerTransaction(ctx, e);
		
		reglementFactureOperation.findReglementsByFactureCode(ctx, factureCode)
		.stream()
		.map(ReglementFactureBean::getTransaction)
		.filter(reservee)
		.forEach(annuler);
		
	}
	
	private void solderFacture(ClientContext ctx, FactureBean bean) {		
		approuverReglements(ctx, bean);
		
		bean.getStatusDate().setValue(LocalDateTime.now());
		bean.getStatus().setValue(FactureStatusEnumVd.SOLD);		
		operation.solderFacture(ctx, bean);		
	}
	
	private void reglerFacture(ClientContext ctx, FactureBean bean, List<PaiementParamBean> paiements) {
		paiements.stream().forEach(e -> reglerFacture(ctx, bean, e));
	}

	private TransactionBean initTransaction(FactureBean bean) {
		var typeFacture = bean.getType().getValue();
		var tx = new TransactionBean();
		
		switch (typeFacture) {
		case ACHAT:
			tx.getType().setValue(TransactionTypeEnumVd.DEPENSE);
			tx.getRubrique().getRubriqueCode().setValue("ACHAT");
			break;
			
		case VENTE:
			tx.getType().setValue(TransactionTypeEnumVd.RECETTE);
			tx.getRubrique().getRubriqueCode().setValue("VENTE");
			break;

		default:
			throw new RuntimeException(String.format("Type de commande %s non prix en charge", typeFacture));
		}

		return tx;
	}
	
	private void reglerFacture(ClientContext ctx, FactureBean bean, PaiementParamBean paiement) {
		var tx = initTransaction(bean);
		tx.getStatus().setValue(TransactionStatusEnumVd.RESERVEE);
		tx.setCompte(paiement.getCompte());
		tx.setPartner(bean.getPartner());
		
		tx.getTransactionCode().setValue(transactionOperation.generateTransactionCode());
		tx.getNote().setValue(bean.getDescription().getValue());
		tx.getDate().setValue(bean.getDate().getValue());
		tx.getMontant().setValue(paiement.getMontant().getValue());
		
		transactionOperation.createTransaction(ctx, tx);
		
		var rg = new ReglementFactureBean(); // Joindre à la commande
		rg.setTransaction(tx);
		rg.getFactureCode().setValue(bean.getFactureCode().getValue());
		
		reglementFactureOperation.createReglement(ctx, rg);
	}
}
