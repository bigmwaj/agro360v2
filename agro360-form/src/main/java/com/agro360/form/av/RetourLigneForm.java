package com.agro360.form.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.RetourLigneBean;
import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.form.common.AbstractForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.stock.ConversionOperation;
import com.agro360.vd.av.RetourStatusEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class RetourLigneForm extends AbstractForm{
	
	@Autowired
	private LigneOperation ligneOperation;
	
	@Autowired
	private ConversionOperation conversionOperation;

	public RetourLigneBean initCreateFormBean(ClientContext ctx, 
			String commandeCode, 
			Long ligneId) {
		
		var bean = new RetourLigneBean();
		var ligne = ligneOperation.findLigneByCode(ctx, commandeCode, ligneId);

		bean.setLigne(ligne);
		bean.getPrixUnitaire().setValue(BigDecimal.ZERO);
		
		bean.setAction(ClientOperationEnumVd.CREATE);
		
		bean.getQuantite().setRequired(true);
		bean.getQuantite().setValue(1.0);
		
		bean.setUnite(ligne.getUnite());
		bean.getUnite().getUniteCode().setRequired(true);
		
		bean.getPrixUnitaire().setValue(ligne.getPrixUnitaire().getValue());
		bean.getPrixUnitaire().setEditable(false);
		
		bean.getStatus().setValue(RetourStatusEnumVd.BRLN);
		bean.getStatus().setEditable(false);
		
		bean.getDate().setValue(LocalDateTime.now());
		bean.getDate().setEditable(false);
		
		initUniteOption(ctx, bean);
		
		return bean;
	}
	
	private void initUniteOption(ClientContext ctx, RetourLigneBean bean) {
		var articleCode = bean.getLigne().getArticle().getArticleCode().getValue();
		Function<UniteBean, String> label = e -> e.getUniteCode().getValue() + " " + e.getDescription().getValue();
		Function<UniteBean, Object> key = e -> e.getUniteCode().getValue();
		var options = conversionOperation.findConversionsByArticleCode(ctx, articleCode)
				.stream()
				.map(ConversionBean::getUnite)
				.collect(Collectors.toMap(key, label));
		
		options.put(key.apply(bean.getLigne().getArticle().getUnite()), 
				label.apply(bean.getLigne().getArticle().getUnite()));
		
		bean.getUnite().getUniteCode().setValueOptions(options);
	}
	
	public RetourLigneBean initUpdateFormBean(ClientContext ctx, 
			String commandeCode, 
			Long ligneId,
			RetourLigneBean bean) {
		
		bean.setAction(ClientOperationEnumVd.SYNC);
		
		bean.getQuantite().setRequired(true);
		
		bean.getUnite().getUniteCode().setRequired(true);
		
		bean.getPrixUnitaire().setEditable(false);
		
		bean.getStatus().setEditable(false);
		
		bean.getDate().setValue(LocalDateTime.now());
		bean.getDate().setEditable(false);
		
		initUniteOption(ctx, bean);
		
		return bean;
	}

}
