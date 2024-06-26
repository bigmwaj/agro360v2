package com.agro360.form.stock;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleCategoryBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.TaxeOperation;
import com.agro360.operation.logic.stock.ArticleCategoryOperation;
import com.agro360.operation.logic.stock.ArticleOperation;
import com.agro360.operation.logic.stock.ArticleTaxeOperation;
import com.agro360.operation.logic.stock.ConversionOperation;
import com.agro360.operation.logic.stock.UniteOperation;
import com.agro360.operation.logic.stock.VariantOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class ArticleForm extends AbstractForm{

	@Autowired
	private ArticleOperation operation;
	
	@Autowired
	private VariantOperation variantOperation;

	@Autowired
	private ConversionOperation conversionOperation;
	
	@Autowired
	private ConversionForm conversionForm;

	@Autowired
	private ArticleTaxeOperation articleTaxeOperation;
	
	@Autowired
	private UniteOperation uniteService;
	
	@Autowired
	private TaxeOperation taxeService;
	
	@Autowired
	private ArticleCategoryOperation partnerCategoryOperation;

	@MetadataBeanName("stock/article-search")
	public ArticleSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = StockMapper.buildArticleSearchBean();
		return bean;
	}

	@MetadataBeanName("stock/article")
	public ArticleBean initUpdateFormBean(ClientContext ctx, String articleCode) {
		var bean = operation.findArticleByCode(ctx, articleCode);
		bean.setAction(ClientOperationEnumVd.UPDATE);
		initVariantsForm(ctx, bean);
		initConversionsForm(ctx, bean);
		initArticleTaxesForm(ctx, bean);
		
		initUniteOption(ctx, bean);
		
		var root = partnerCategoryOperation.findAssignmentsFromLeaves(ctx, bean);
		bean.setCategoriesHierarchie(root);
		
		return bean;
	}

	public ArticleBean initDeleteFormBean(ClientContext ctx, String articleCode) {
		return operation.findArticleByCode(ctx, articleCode);
	}

	@MetadataBeanName("stock/article")
	public ArticleBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		
		var bean = copyFrom.map(e -> operation.findArticleByCode(ctx, e))
				.orElse(new ArticleBean());
		
		initVariantsForm(ctx, bean);
		initConversionsForm(ctx, bean);
		initArticleTaxesForm(ctx, bean);
		
		initUniteOption(ctx, bean);
		
		bean.getArticleCode().setValue(null);
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getVariants().stream().forEach(AbstractBean.setActionToCreate);
		bean.getConversions().stream().forEach(AbstractBean.setActionToCreate);
		
		Function<ArticleBean, ArticleCategoryBean> getPartnerCat;
		getPartnerCat = e -> partnerCategoryOperation.findAssignmentsFromLeaves(ctx, e);
		
		Supplier<ArticleCategoryBean> getDefaultCat;
		getDefaultCat = () -> partnerCategoryOperation.findRoot(ctx, 5);
		
		var root = copyFrom.map(this::simpleMap).map(getPartnerCat).orElseGet(getDefaultCat);
		bean.setCategoriesHierarchie(root);
		
		return bean;
	}
	
	private ArticleBean simpleMap(String articleCode) {
		var bean = new ArticleBean();
		bean.getArticleCode().setValue(articleCode);
		
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
		var variants = variantOperation.findVariantsByArticleCode(ctx, articleCode);
		bean.getVariants().addAll(variants);
	}
	
	private void initConversionsForm(ClientContext ctx, ArticleBean bean) {
		var articleCode = bean.getArticleCode().getValue();
		if( articleCode == null ) {
			return;
		}
		var conversions = conversionOperation.findConversionsByArticleCode(ctx, articleCode);
		for (var conversion : conversions) {
			conversionForm.initUniteOption(ctx, conversion);
			bean.getConversions().add(conversion);
		}
	}
	
	private void initArticleTaxesForm(ClientContext ctx, ArticleBean bean) {
		var selected = new ArrayList<String>();
		
		var articleCode = bean.getArticleCode().getValue();
		if( articleCode != null ) {
			var articleTaxes = articleTaxeOperation.findArticleTaxesByArticleCode(ctx, articleCode);
			for (var taxe : articleTaxes) {
				bean.getTaxes().add(taxe);
				taxe.getSelected().setValue(true);
				selected.add(taxe.getTaxe().getTaxeCode().getValue());
			}
		}
		
		Predicate<TaxeBean> isNotSelected = e -> !selected.stream()
				.anyMatch(e.getTaxeCode().getValue()::equals);
		
		taxeService.findAllTaxes(ctx) 
			.stream().filter(isNotSelected)
			.map(this::initEdit)
			.forEach(bean.getTaxes()::add);
	}
	
	private ArticleTaxeBean initEdit(TaxeBean taxe) {
		var bean = new ArticleTaxeBean();
		bean.setTaxe(taxe);
		bean.setAction(ClientOperationEnumVd.SYNC);
		return bean;
	}
}
