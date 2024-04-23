package com.agro360.service.stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.InventaireOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.vd.common.ClientOperationEnumVd;

@Transactional(rollbackFor = Exception.class)
@Service
public class InventaireService extends AbstractService {

	@Autowired
	private InventaireOperation operation;
	
	@Autowired
	private InventaireServiceHelper serviceHelper;
	
	public List<InventaireBean> search(ClientContext ctx, Optional<InventaireSearchBean> searchBean) {
		return operation
				.findInventairesByCriteria(ctx, searchBean.orElse(new InventaireSearchBean()));
	}
	
	public List<ArticleBean> findNonStockedArticles(ClientContext ctx, String magasinCode) {
		return operation.findNonStockedArticles(ctx, magasinCode);
	}

	public List<VariantBean> findNonStockedArticleVariants(ClientContext ctx, 
			String magasinCode, 
			String articleCode) {
		return operation.findNonStockedArticleVariants(ctx, magasinCode, articleCode);
	}

	public void save(ClientContext ctx, String magasinCode, String articleCode, List<VariantBean> variants) {
		
		var msgTpl = "Les variantes sélectionnées de l'article %s ont été ajoutées au magasin %s avec succès!";
		ctx.success(String.format(msgTpl, articleCode, magasinCode));
		for (var variant : variants) {
			if( !ClientOperationEnumVd.CREATE.equals(variant.getAction())) {
				continue;
			}
			var variantCode = variant.getVariantCode().getValue();
			
			var bean = new InventaireBean();
			bean.getMagasin().getMagasinCode().setValue(magasinCode);
			bean.getArticle().getArticleCode().setValue(articleCode);
			bean.getVariantCode().setValue(variantCode);
			
			bean.getQuantite().setValue(Double.valueOf(0));
			bean.getPrixUnitaireAchat().setValue(BigDecimal.ZERO);
			bean.getPrixUnitaireVente().setValue(BigDecimal.ZERO);
			
			operation.createInventaire(ctx, bean);
		}		
	}

	public void ajusterQuantite(ClientContext ctx, InventaireBean bean) {
		serviceHelper.ajusterQuantite(ctx, bean);
	}

	public void ajusterPrix(ClientContext ctx, InventaireBean bean) {
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();
		
		var freshBean = operation.findInventaireByCode(ctx, magasinCode, articleCode, variantCode);
		
		freshBean.getPrixUnitaireVente().setValue(bean.getPrixUnitaireVente().getValue());
		operation.updateInventairePrixVente(ctx, freshBean);
		
		var prix = freshBean.getPrixUnitaireVente().getValue();
		var msgTpl = "Le prix de la variante <strong>%s</strong> de l'article <strong>%s</strong> a été ajusté au magasin <strong>%s</strong> avec succès!"
				+ " Le nouveau prix de vente est <strong>%,.2f</strong>";
		ctx.success(String.format(msgTpl, variantCode, articleCode, magasinCode, prix.doubleValue()));
	}
	
}
