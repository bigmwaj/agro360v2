package com.agro360.service.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;
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
	
	public List<CommandeBean> search(ClientContext ctx, Optional<CommandeSearchBean> searchBean) {
		var q = searchBean.orElse(new CommandeSearchBean());
		return operation.findCommandesByCriteria(ctx, q);
	}
	
	private void initPrix(ClientContext ctx, CommandeBean bean) {
		bean.getLignes().stream().forEach(e -> ligneServiceHelper.initPrix(ctx, e));
        
        var prixTotalHT = bean.getLignes().stream()
        	.map(LigneBean::getPrixTotalHT)
        	.map(FieldMetadata::getValue)
        	.map(BigDecimal.class::cast)
        	.reduce(BigDecimal::add)
    		.orElse(BigDecimal.ZERO);
        
        var prixTotalTTC = bean.getLignes().stream()
        	.map(LigneBean::getPrixTotalTTC)
        	.map(FieldMetadata::getValue)
        	.map(BigDecimal.class::cast)
        	.reduce(BigDecimal::add)
    		.orElse(BigDecimal.ZERO);
        
        var taxe = bean.getLignes().stream()
        	.map(LigneBean::getTaxe)
        	.map(FieldMetadata::getValue)
        	.map(BigDecimal.class::cast)
        	.reduce(BigDecimal::add)
    		.orElse(BigDecimal.ZERO);
        
        var pritTotal = bean.getLignes().stream()
    		.map(LigneBean::getPrixTotal)
    		.map(FieldMetadata::getValue)
    		.map(BigDecimal.class::cast)
    		.reduce(BigDecimal::add)
    		.orElse(BigDecimal.ZERO);
        
        var remise = bean.getLignes().stream()
    		.map(LigneBean::getRemiseMontant)
    		.map(FieldMetadata::getValue)
    		.map(BigDecimal.class::cast)
    		.reduce(BigDecimal::add)
    		.orElse(BigDecimal.ZERO);

        if( Objects.isNull(bean.getRemiseMontant().getValue()) ){
            bean.getRemiseMontant().setValue(BigDecimal.ZERO);
        }   

        if( RemiseTypeEnumVd.TAUX.equals(bean.getRemiseType().getValue()) && bean.getRemiseTaux().getValue() > 0 ){
            var remiseMontant = pritTotal.multiply(BigDecimal.valueOf(bean.getRemiseTaux().getValue()))
            		.divide(BigDecimal.valueOf(100));
            bean.getRemiseMontant().setValue(remiseMontant);
        }else if( Objects.isNull(bean.getRemiseMontant().getValue()) ){
            bean.getRemiseMontant().setValue(BigDecimal.ZERO);
        }
        
        remise = remise.add(bean.getRemiseMontant().getValue());
        
        bean.getPrixTotalHT().setValue(prixTotalHT);
        bean.getRemise().setValue(remise);
        bean.getTaxe().setValue(taxe);
        bean.getPrixTotalTTC().setValue(prixTotalTTC);
        bean.getPrixTotal().setValue(pritTotal);
        
        bean.getPrixTotalTTC().setValueChanged(true);
        bean.getPrixTotal().setValueChanged(true);
        bean.getTaxe().setValueChanged(true);
        bean.getRemiseMontant().setValueChanged(true);
        bean.getRemise().setValueChanged(true);
        bean.getPrixTotalTTC().setValueChanged(true);
	}
	
	public void save(ClientContext ctx, CommandeBean bean) {
		switch (bean.getAction()) {
		case CREATE:
			initPrix(ctx, bean);
			operation.createCommande(ctx, bean);
			createLignesCommande(ctx, bean);
			
			var msgTpl = "La commande <strong>%s</strong> a été créée avec succès!";
			ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
			break;
			
		case UPDATE:
			initPrix(ctx, bean);
			operation.updateCommande(ctx, bean);
			syncLignesCommande(ctx, bean);
			
			msgTpl = "La commande <strong>%s</strong> a été modifiée avec succès!";
			ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ANNL:
				operation.annulerCommande(ctx, bean);
				
				msgTpl = "La commande <strong>%s</strong> a été annulée avec succès!";
				ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
				break;
				
			case APPR:
				var freshBean = operation.findCommandeByCode(ctx, bean.getCommandeCode().getValue());
				var paiementCash = freshBean.getPaiementComptant().getValue();
				if( !Objects.isNull(paiementCash) && !BigDecimal.ZERO.equals(paiementCash) ) {
					reglerCommandeComptant(ctx, bean);
				}
				
				if( paiementCash == null ) {
					paiementCash = BigDecimal.ZERO;
				}
				
				var prixTotalNet = freshBean.getPrixTotal().getValue();
				var reste = prixTotalNet.subtract(paiementCash);
				if( reste.compareTo(BigDecimal.ZERO) > 0 ) {
					reglerCommandeACredit(ctx, freshBean, reste);
				}
				
				operation.approuverCommande(ctx, bean);
				
				if( CommandeTypeEnumVd.VENTE.equals(bean.getType().getValue()) ) {
					inventaireServiceHelper.enregistrerSortiesMagasin(ctx, bean);
				}
				
				msgTpl = "La commande <strong>%s</strong> a été approuvée avec succès!";
				ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
				break;
				
			case ATAP:
				operation.demanderApprobationCommande(ctx, bean);
				
				msgTpl = "La commande <strong>%s</strong> a été envoyée en approbation avec succès!";
				ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
				break;
				
			case RECP:
				operation.receptionnerCommande(ctx, bean);
				
				msgTpl = "La commande <strong>%s</strong> a été partiellement receptionnée avec succès!";
				ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
				break;
				
			case REJT:
				operation.rejeterCommande(ctx, bean);
				
				msgTpl = "La commande <strong>%s</strong> a été rejetée avec succès!";
				ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
				break;
				
			case RECT:
				operation.receptionnerCommande(ctx, bean);
				
				msgTpl = "La commande <strong>%s</strong> a été totalement receptionnée avec succès!";
				ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
				break;

			default:
				break;
			}
			break;

		case DELETE:
			deleteLignesCommande(ctx, bean);
			operation.deleteCommande(ctx, bean);
			
			msgTpl = "La commande <strong>%s</strong> a été supprimée avec succès!";
			ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
			break;
			
		default:
			
		}
	}
	
	private void reglerCommandeComptant(ClientContext clientContext, CommandeBean bean) {
		var tx = new TransactionBean();
		
		var rubriqueCode = "VENTE";
		tx.getRubrique().getRubriqueCode().setValue(rubriqueCode);
		
		tx.setCompte(bean.getCompte());
		tx.setPartner(bean.getPartner());
		
		tx.getTransactionCode().setValue(transactionOperation.generateTransactionCode());
		tx.getStatus().setValue(TransactionStatusEnumVd.APPROUVEE);
		tx.getType().setValue(TransactionTypeEnumVd.RECETTE);
		tx.getNote().setValue(bean.getDescription().getValue());
		tx.getDate().setValue(bean.getDate().getValue());
		tx.getMontant().setValue(bean.getPaiementComptant().getValue());
		
		transactionOperation.createTransaction(clientContext, tx);
		
		var rg = new ReglementCommandeBean();
		rg.setTransaction(tx);
		rg.getCommandeCode().setValue(bean.getCommandeCode().getValue());
		rg.getMontant().setValue(bean.getPaiementComptant().getValue());
		
		reglementCommandeOperation.createCommande(clientContext, rg);
	
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
		for (LigneBean ligne : bean.getLignes()) {
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
}
