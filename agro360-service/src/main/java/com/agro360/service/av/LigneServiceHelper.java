package com.agro360.service.av;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureTaxeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.LigneTaxeBean;
import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.FactureTaxeOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.av.LigneTaxeOperation;
import com.agro360.operation.logic.stock.ArticleTaxeOperation;
import com.agro360.vd.av.CommandeTypeEnumVd;

@Service
public class LigneServiceHelper {
	
	@Autowired
	private LigneOperation operation;
	
	@Autowired
	private ArticleTaxeOperation articleTaxeOperation;
	
	@Autowired
	private LigneTaxeOperation ligneTaxeOperation;
	
	@Autowired
	private FactureTaxeOperation factureTaxeOperation;
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
	
	public void initChampsCalcules(ClientContext ctx, CommandeBean commande, LigneBean bean) {
		var type = commande.getType().getValue();
		initChampsCalcules(ctx, type, bean);
	}
	
	public void initChampsCalcules(ClientContext ctx, CommandeTypeEnumVd type, LigneBean bean) {
		var qte = BigDecimal.valueOf(bean.getQuantite().getValue());
		var pu = bean.getPrixUnitaire().getValue();
		var prixTotalHT = pu.multiply(qte);
		
		if( Objects.isNull(bean.getRemiseMontant().getValue()) ){
            bean.getRemiseMontant().setValue(BigDecimal.ZERO);
        } 
		if( Objects.isNull(bean.getRemiseTaux().getValue()) ){
			bean.getRemiseTaux().setValue(Double.valueOf(0));
		}  
		var remise = BigDecimal.ZERO;
		
		switch (bean.getRemiseType().getValue()) {
		case MONTANT:
			bean.getRemiseTaux().setValue(Double.valueOf(0));
			remise = bean.getRemiseMontant().getValue();
			break;
			
		case TAUX:
			bean.getRemiseMontant().setValue(BigDecimal.ZERO);
			if( bean.getRemiseTaux().getValue() > 0 ){
				var taux = BigDecimal.valueOf(bean.getRemiseTaux().getValue());
				var hundred = BigDecimal.valueOf(100);
	            remise = prixTotalHT.multiply(taux).divide(hundred);
	        }
			break;

		default:
			break;
		}
        
        var prixTotalHTAvecRemise = prixTotalHT.subtract(remise);
        var taxe = calculerMontantTaxesAppliquees(ctx, bean, prixTotalHTAvecRemise);
        var prixTotal = prixTotalHTAvecRemise.add(taxe);
        
        bean.getPrixTotalHT().setValue(prixTotalHT);
        bean.getRemise().setValue(remise);
        bean.getTaxe().setValue(taxe); 
        bean.getPrixTotal().setValue(prixTotal);
        
        bean.getPrixTotalHT().setValueChanged(true);
        bean.getRemise().setValueChanged(true);
        bean.getTaxe().setValueChanged(true);
        bean.getPrixTotal().setValueChanged(true);
        
        bean.getRemiseTaux().setValueChanged(true);
        bean.getRemiseMontant().setValueChanged(true);
	}

	public void createLignesCommande(ClientContext ctx, CommandeBean commande, List<LigneBean> beans) {
		for (var bean : beans) {
			operation.createLigne(ctx, commande, bean);
			createLigneTaxesAppliquees(ctx, commande, bean);
		}
	}
	
	public void syncLignesCommande(ClientContext ctx, CommandeBean commande, List<LigneBean> beans) {
	for (var bean : beans) {
		switch (bean.getAction()) {
			case CREATE:			
				operation.createLigne(ctx, commande, bean);
				createLigneTaxesAppliquees(ctx, commande, bean);
				break;
				
			case UPDATE:
				operation.updateLigne(ctx, commande, bean);
				updateLigneTaxesAppliquees(ctx, commande, bean);
				break;
				
			case DELETE:
				operation.deleteLigne(ctx, commande, bean);
				deleteLigneTaxesAppliquees(ctx, commande, bean);
				break;
	
			default:
				break;
			}
		}
	}
	
	private void createLigneTaxesAppliquees(ClientContext ctx, CommandeBean commande, LigneBean bean) {
		Function<TaxeBean, LigneTaxeBean> mapper;
		mapper = e -> map(ctx, commande, bean, e);
		
		Consumer<LigneTaxeBean> save = e -> ligneTaxeOperation.createLigne(ctx, commande, bean, e);
		
		findTaxesAppliquees(ctx, bean).stream().map(mapper).forEach(save);
	}
	
	private FactureTaxeBean map(TaxeBean taxe, BigDecimal montant) {
		var ft = new FactureTaxeBean();
		
		ft.getMontant().setValue(montant);
		ft.setTaxe(taxe);
		
		return ft;
	}
	
	public void assignerFactureTaxesAppliquees(ClientContext ctx, CommandeBean commande, FactureBean facture) {
		
		Function<LigneTaxeBean, BigDecimal> valueFn;
		valueFn = e -> e.getMontant().getValue();
		
		Function<Map.Entry<TaxeBean, BigDecimal>, FactureTaxeBean> mapper;
		mapper = e -> map(e.getKey(), e.getValue());
		
		Consumer<FactureTaxeBean> save = e -> factureTaxeOperation.createFacture(ctx, facture, e);
		
		ligneTaxeOperation.findLigneTaxes(ctx, commande)
		.stream()
		.collect(Collectors.toMap(LigneTaxeBean::getTaxe, valueFn, BigDecimal::add))
		.entrySet()
		.stream()
		.map(mapper)
		.forEach(save);
	}
	
	public void updateLigneTaxesAppliquees(ClientContext ctx, CommandeBean commande, LigneBean bean) {
		ligneTaxeOperation.deleteLignes(ctx, commande, bean);
		createLigneTaxesAppliquees(ctx, commande, bean);
	}
	
	public void deleteLigneTaxesAppliquees(ClientContext ctx, CommandeBean commande, LigneBean bean) {
		ligneTaxeOperation.deleteLignes(ctx, commande, bean);
	}
	
	private BigDecimal calculerMontantTaxesAppliquees(ClientContext ctx, LigneBean bean, BigDecimal prixTotalHTAvecRemise) {
		Function<TaxeBean, BigDecimal> calculerMontant;
		calculerMontant = e -> calculerMontantTaxesAppliquees(ctx, bean, prixTotalHTAvecRemise, e);
		return findTaxesAppliquees(ctx, bean)
			.stream()
			.map(calculerMontant)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
	}
	
	private BigDecimal calculerMontantTaxesAppliquees(ClientContext ctx, LigneBean bean, BigDecimal prixTotalHTAvecRemise, TaxeBean taxe) {
		var hundred = BigDecimal.valueOf(100.0);
		var taux = BigDecimal.valueOf(taxe.getTaux().getValue());
		return prixTotalHTAvecRemise.multiply(taux).divide(hundred, RoundingMode.HALF_UP);
	}
	
	private List<TaxeBean> findTaxesAppliquees(ClientContext ctx, LigneBean bean){
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var taxes = articleTaxeOperation.findArticleTaxesByArticleCode(ctx, articleCode);
		return taxes.stream()
				.map(ArticleTaxeBean::getTaxe)
				.collect(Collectors.toList());
	}
	
	private LigneTaxeBean map(ClientContext ctx, CommandeBean commande, LigneBean ligne, TaxeBean taxe) {
		var ligneTaxe = new LigneTaxeBean();
		var prixTotalHT = ligne.getPrixTotalHT().getValue();
		var remise = ligne.getRemise().getValue();
		var prixTotalHTAvecRemise = prixTotalHT.subtract(remise);
		
		var montant = calculerMontantTaxesAppliquees(ctx, ligne, prixTotalHTAvecRemise, taxe);
		
		ligneTaxe.getMontant().setValue(montant);
		ligneTaxe.getLigneId().setValue(ligne.getLigneId().getValue());
		ligneTaxe.setTaxe(taxe);
		ligneTaxe.getCommandeCode().setValue(commande.getCommandeCode().getValue());
		
		return ligneTaxe;
	}
	
}
