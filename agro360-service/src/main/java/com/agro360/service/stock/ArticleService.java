package com.agro360.service.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleOperation;
import com.agro360.operation.logic.stock.ConversionOperation;
import com.agro360.operation.logic.stock.VariantOperation;
import com.agro360.service.common.AbstractService;

@Service
public class ArticleService extends AbstractService {

	@Autowired
	ArticleOperation service;
	
	@Autowired
	VariantOperation variantOperation;
	
	@Autowired
	ConversionOperation conversionOperation;
	
	public List<ArticleBean> searchAction(ClientContext ctx, Optional<ArticleSearchBean> searchBean) {
		return service
				.findArticlesByCriteria(ctx, searchBean.orElse(new ArticleSearchBean()));
	}

	public void saveAction(ClientContext ctx, ArticleBean bean) {
		switch (bean.getAction()) {
		case CREATE:
			service.createArticle(ctx, bean);
			addAllVariantsArticle(ctx, bean);
			addAllConversionsArticle(ctx, bean);
			break;

		case UPDATE:
			service.updateArticle(ctx, bean);
			syncAllVariantsArticle(ctx, bean);
			syncAllConversionsArticle(ctx, bean);
			break;

		case DELETE:
			deleteAllVariantsArticle(ctx, bean);
			deleteAllConversionsArticle(ctx, bean);
			service.deleteArticle(ctx, bean);
			break;
			
		default:
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
	
}
