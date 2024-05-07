package com.agro360.form.av;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.stock.ConversionOperation;

public abstract class AbstractPostCommandeEntityForm extends AbstractForm{
	
	protected abstract ConversionOperation getConversionOperation();
	
	protected abstract LigneOperation getLigneOperation();
	
	final protected Map<Object, String> getUniteOption(ClientContext ctx, LigneBean bean) {
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var unites = getConversionOperation().findConversionsByArticleCode(ctx, articleCode)
				.stream()
				.map(ConversionBean::getUnite)
				.collect(Collectors.toCollection(ArrayList::new));
		
		unites.add(bean.getArticle().getUnite());
		
		return unites
				.stream()
				.map(StockMapper::asOption)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	final protected Map<Object, String> getLigneOptions(ClientContext ctx, String commandeCode) {
		Function<LigneBean, String> label = e -> e.getArticle().getArticleCode().getValue() + " - " + e.getVariantCode().getValue();
		Function<LigneBean, Object> key = e -> e.getLigneId().getValue();
		
		return getLigneOperation().findLignesCommande(ctx, commandeCode)
				.stream()
				.collect(Collectors.toMap(key, label));
	}

	public final LigneBean initLigne(ClientContext ctx, String commandeCode, Long ligneId) {
		var ligneOptions = getLigneOptions(ctx, commandeCode);
		var ligne = getLigneOperation().findLigneByCode(ctx, commandeCode, ligneId);
		ligne.getLigneId().setValueOptions(ligneOptions);
		var uniteOptions = getUniteOption(ctx, ligne);
		ligne.getUnite().getUniteCode().setValueOptions(uniteOptions);
		ligne.getLigneId().setEditable(true);
		return ligne;
	}

}
