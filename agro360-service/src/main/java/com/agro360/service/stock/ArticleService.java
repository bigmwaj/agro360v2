package com.agro360.service.stock;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleCategoryBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleCategoryOperation;
import com.agro360.operation.logic.stock.ArticleOperation;
import com.agro360.operation.logic.stock.ArticleTaxeOperation;
import com.agro360.operation.logic.stock.ConversionOperation;
import com.agro360.operation.logic.stock.VariantOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class ArticleService extends AbstractService {

	@Autowired
	private ArticleOperation operation;
	
	@Autowired
	private VariantOperation variantOperation;
	
	@Autowired
	private ConversionOperation conversionOperation;
	
	@Autowired
	private ArticleTaxeOperation articleTaxeOperation;

	@Autowired
	private ArticleCategoryOperation articleCategoryOperation;
	
	public List<ArticleBean> search(ClientContext ctx, ArticleSearchBean searchBean) {
		return operation
				.findArticlesByCriteria(ctx, searchBean);
	}

	public void save(ClientContext ctx, ArticleBean bean) {
		var articleCode = bean.getArticleCode().getValue();
		switch (bean.getAction()) {
		case CREATE:
			operation.createArticle(ctx, bean);
			addAllVariantsArticle(ctx, bean);
			addAllConversionsArticle(ctx, bean);
			addAllSelectedTaxesArticle(ctx, bean);

			var categoryCodes = retrieveSelectedCategoryCodes(bean.getCategoriesHierarchie());
			articleCategoryOperation.syncAssignments(ctx, bean, categoryCodes);
			
			var msgTpl = "L'article %s a été créé avec succès!";
			ctx.success(String.format(msgTpl, articleCode));
			
			break;

		case UPDATE:
			operation.updateArticle(ctx, bean);
			syncAllVariantsArticle(ctx, bean);
			syncAllConversionsArticle(ctx, bean);
			syncAllTaxesArticle(ctx, bean);
			
			categoryCodes = retrieveSelectedCategoryCodes(bean.getCategoriesHierarchie());
			articleCategoryOperation.syncAssignments(ctx, bean, categoryCodes);
			
			msgTpl = "L'article %s a été modifié avec succès!";
			ctx.success(String.format(msgTpl, articleCode));
			break;

		case DELETE:
			deleteAllVariantsArticle(ctx, bean);
			deleteAllConversionsArticle(ctx, bean);
			deleteAllTaxesArticle(ctx, bean);
			articleCategoryOperation.deleteAll(ctx, bean);
			operation.deleteArticle(ctx, bean);
			
			msgTpl = "L'article %s a été supprimé avec succès!";
			ctx.success(String.format(msgTpl, articleCode));
			break;
			
		default:
			break;
		}
	}
	
	private void addAllVariantsArticle(ClientContext ctx, ArticleBean bean) {
		for(var variant : bean.getVariants()) {
			variantOperation.createVariant(ctx, bean, variant);
		}
	}
	
	private void addAllConversionsArticle(ClientContext ctx, ArticleBean bean) {
		for (var conversion : bean.getConversions()) {
			conversionOperation.createConversion(ctx, bean, conversion);
		}
	}
	
	private void addAllSelectedTaxesArticle(ClientContext ctx, ArticleBean bean) {
		for (var taxe : bean.getTaxes()) {
			if( Boolean.TRUE.equals(taxe.getSelected().getValue()) ) {
				articleTaxeOperation.createArticleTaxe(ctx, bean, taxe);
			}
		}
	}
	
	private void deleteAllVariantsArticle(ClientContext ctx, ArticleBean bean) {
		for(var variant : bean.getVariants()) {
			variantOperation.deleteVariant(ctx, bean, variant);
		}
	}
	
	private void deleteAllConversionsArticle(ClientContext ctx, ArticleBean bean) {
		for (var conversion : bean.getConversions()) {
			conversionOperation.deleteConversion(ctx, bean, conversion);
		}
	}
	
	private void deleteAllTaxesArticle(ClientContext ctx, ArticleBean bean) {
		for (var taxe : bean.getTaxes()) {
			articleTaxeOperation.deleteArticleTaxe(ctx, bean, taxe);
		}
	}
	
	private void syncAllVariantsArticle(ClientContext ctx, ArticleBean bean) {
		for(var variant : bean.getVariants()) {
			switch (variant.getAction()) {
			case CREATE:
				variantOperation.createVariant(ctx, bean, variant);
				break;
				
			case UPDATE:
				variantOperation.updateVariant(ctx, bean, variant);
				break;
				
			case DELETE:
				variantOperation.deleteVariant(ctx, bean, variant);
				break;

			default:
				break;
			}
		}
	}

	private void syncAllTaxesArticle(ClientContext ctx, ArticleBean bean) {
		var existing = articleTaxeOperation.findArticleTaxesByArticleCode(ctx, bean.getArticleCode().getValue())
				.stream()
				.map(ArticleTaxeBean::getTaxe)
				.map(TaxeBean::getTaxeCode)
				.map(FieldMetadata::getValue)
				.map(String.class::cast)
				.collect(Collectors.toList());
		
		Predicate<ArticleTaxeBean> existsInDb = e -> existing.stream()
				.anyMatch(e.getTaxe().getTaxeCode().getValue()::equals);
		
		for( var taxe : bean.getTaxes() ) {
			if( Boolean.TRUE.equals(taxe.getSelected().getValue())) {
				if( !existsInDb.test(taxe) ) {
					articleTaxeOperation.createArticleTaxe(ctx, bean, taxe);
				}
			}else {
				if( existsInDb.test(taxe) ) {
					articleTaxeOperation.deleteArticleTaxe(ctx, bean, taxe);
				}
			}			
		}
	}
	
	private void syncAllConversionsArticle(ClientContext ctx, ArticleBean bean) {
		for (var conversion : bean.getConversions()) {
			switch (conversion.getAction()) {
			case CREATE:
				conversionOperation.createConversion(ctx, bean, conversion);
				break;
				
			case UPDATE:
				conversionOperation.updateConversion(ctx, bean, conversion);
				break;
				
			case DELETE:
				conversionOperation.deleteConversion(ctx, bean, conversion);
				break;

			default:
				break;
			}
		}
	}

	public Map<Object, String> searchAsOptions(ClientContext ctx, ArticleSearchBean searchBean) {
		return operation.findArticlesByCriteria(ctx, searchBean)
				.stream()
				.map(StockMapper::asOption)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public Map<Object, String> getVariantArticleAsOption(ClientContext ctx, String articleCode) {
		return variantOperation.findVariantsByArticleCode(ctx, articleCode)
				.stream()
				.map(StockMapper::asOption)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public Map<Object, String> getUniteArticleAsOption(ClientContext ctx, String articleCode) {
		return operation.findUnitesArticleByCode(ctx, articleCode)
				.stream()
				.map(StockMapper::asOption)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public List<VariantBean> getVariantsByQuery(ClientContext ctx, String query) {
		return variantOperation.findVariantsByQuery(ctx, query);
	}

	private void retrieveSelectedCategoryCodes(Set<String> categoryCodes, ArticleCategoryBean bean){
		if( Boolean.TRUE.equals(bean.getSelected().getValue()) ) {
			categoryCodes.add(bean.getCategoryCode().getValue());			
		}
		
		for (var b : bean.getChildren()) {
			retrieveSelectedCategoryCodes(categoryCodes, b);
		}
	}
	
	private Collection<String> retrieveSelectedCategoryCodes(ArticleCategoryBean bean){
		Set<String> categoryCodes = new HashSet<>();
		retrieveSelectedCategoryCodes(categoryCodes, bean);
		return categoryCodes;
	}
	
}
