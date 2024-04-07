package com.agro360.form.stock;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.mapper.stock.ArticleMapper;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleOperation;
import com.agro360.operation.logic.stock.ConversionOperation;
import com.agro360.operation.logic.stock.UniteOperation;
import com.agro360.operation.logic.stock.VariantOperation;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class ArticleForm {

	@Autowired
	ArticleMapper mapper;

	@Autowired
	ArticleOperation operation;
	
	@Autowired
	VariantOperation variantService;

	@Autowired
	ConversionOperation conversionService;
	
	@Autowired
	ConversionForm conversionForm;
	
	@Autowired
	UniteOperation uniteService;

	public ArticleSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = mapper.mapToSearchBean();
		return bean;
	}

	public ArticleBean initUpdateFormBean(ClientContext ctx, String articleCode) {
		var bean = operation.findArticleByCode(ctx, articleCode);

		initVariantsForm(ctx, bean);
		initConversionsForm(ctx, bean);
		
		initUniteOption(ctx, bean);
		
		return bean;
	}

	public ArticleBean initDeleteFormBean(ClientContext ctx, String articleCode) {
		return operation.findArticleByCode(ctx, articleCode);
	}

	public ArticleBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		
		var bean = copyFrom.map(e -> operation.findArticleByCode(ctx, e))
				.orElse(mapper.map(new ArticleDto()));
		
		initVariantsForm(ctx, bean);
		initConversionsForm(ctx, bean);
		
		initUniteOption(ctx, bean);
		
		bean.getArticleCode().setValue(null);
		bean.setAction(EditActionEnumVd.CREATE);
		bean.getVariants().stream().forEach(AbstractBean.setActionToCreate);
		bean.getConversions().stream().forEach(AbstractBean.setActionToCreate);

		return bean;
	}
	
	private void initUniteOption(ClientContext ctx, ArticleBean bean) {
		Function<UniteBean, Object> codeFn = e -> e.getUniteCode().getValue();
		Function<UniteBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = uniteService.findUnitesByCriteria(ctx, new UniteSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		
		bean.getUnite().getUniteCode().setValueOptions(options);
	}
	
	private void initVariantsForm(ClientContext ctx, ArticleBean bean) {
		var articleCode = bean.getArticleCode().getValue();
		if( articleCode == null ) {
			return;
		}
		var variants = variantService.findVariantsByArticleCode(ctx, articleCode);
		bean.getVariants().addAll(variants);
	}
	
	private void initConversionsForm(ClientContext ctx, ArticleBean bean) {
		var articleCode = bean.getArticleCode().getValue();
		if( articleCode == null ) {
			return;
		}
		var conversions = conversionService.findConversionsByArticleCode(ctx, articleCode);
		for (var conversion : conversions) {
			conversionForm.initUniteOption(ctx, conversion);
			bean.getConversions().add(conversion);
		}
	}
}
