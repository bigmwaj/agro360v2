package com.agro360.service.av;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleTaxeOperation;

@Service
public class LigneServiceHelper {
	
	@Autowired
	private ArticleTaxeOperation articleTaxeOperation;
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
	
	public void initPrix(ClientContext ctx, LigneBean bean) {
		var prixTotalHT = bean.getPrixUnitaire().getValue()
				.multiply(BigDecimal.valueOf(bean.getQuantite().getValue()));
		
		if( Objects.isNull(bean.getRemiseMontant().getValue()) ){
            bean.getRemiseMontant().setValue(BigDecimal.ZERO);
        }   
		if( Objects.isNull(bean.getRemiseTaux().getValue()) ){
			bean.getRemiseTaux().setValue(Double.valueOf(0));
		}   
		
		bean.getRemise().setValue(BigDecimal.ZERO);
		
		switch (bean.getRemiseType().getValue()) {
		case MONTANT:
			bean.getRemiseTaux().setValue(Double.valueOf(0));
			bean.getRemise().setValue(bean.getRemiseMontant().getValue());
			break;
			
		case TAUX:
			bean.getRemiseMontant().setValue(BigDecimal.ZERO);
			if( bean.getRemiseTaux().getValue() > 0 ){
	            var remise = prixTotalHT.multiply(BigDecimal.valueOf(bean.getRemiseTaux().getValue()))
	            		.divide(BigDecimal.valueOf(100));
	            bean.getRemise().setValue(remise);
	        }
			break;

		default:
			break;
		}
        
        var prixTotalHTAvecRemise = prixTotalHT.subtract(bean.getRemiseMontant().getValue());
        var taxe = calculerTaxe(ctx, bean, prixTotalHTAvecRemise);
        
        bean.getTaxe().setValue(taxe); 
        
        var prixTotal = prixTotalHTAvecRemise.add(bean.getTaxe().getValue());
        bean.getPrixTotal().setValue(prixTotal);
        
        bean.getPrixTotalHT().setValueChanged(true);
        bean.getRemise().setValueChanged(true);
        bean.getTaxe().setValueChanged(true);
        bean.getPrixTotal().setValueChanged(true);
        bean.getRemiseMontant().setValueChanged(true);
	}
	
	private BigDecimal calculerTaxe(ClientContext ctx, LigneBean bean, BigDecimal prixTotalHTAvecRemise) {
		var articleCode = bean.getArticle().getArticleCode().getValue();
		Function<Double, BigDecimal> percent = e -> BigDecimal.valueOf(e).divide(BigDecimal.valueOf(100.0));
		var taxes = articleTaxeOperation.findArticleTaxesByArticleCode(ctx, articleCode);
		return taxes
			.stream()
			.map(ArticleTaxeBean::getTaxe)
			.map(TaxeBean::getTaux)
			.map(FieldMetadata::getValue)
			.map(percent)
			.map(prixTotalHTAvecRemise::multiply)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
	}
}
