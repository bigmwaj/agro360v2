package com.agro360.service.stock;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ImportInventaireOperation;
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
	
	@Autowired
	private ImportInventaireOperation importInventaireOperation;
	
	public List<InventaireBean> search(ClientContext ctx, InventaireSearchBean searchBean) {
		return operation.findInventairesByCriteria(ctx, searchBean);
	}
	
	public List<ArticleBean> findNonStockedArticles(ClientContext ctx, String magasinCode) {
		return operation.findNonStockedArticles(ctx, magasinCode);
	}

	public List<InventaireBean> findNonStockedArticleVariants(ClientContext ctx, 
			String magasinCode, 
			String articleCode) {
		return operation.findNonStockedArticleVariants(ctx, magasinCode, articleCode);
	}

	public void save(ClientContext ctx, String magasinCode, String articleCode, List<InventaireBean> beans) {
		for (var bean : beans) {
			if( !ClientOperationEnumVd.CREATE.equals(bean.getAction())) {
				continue;
			}
			var variantCode = bean.getVariantCode().getValue();
			
			bean.getMagasin().getMagasinCode().setValue(magasinCode);
			bean.getArticle().getArticleCode().setValue(articleCode);
			bean.getVariantCode().setValue(variantCode);
			
			bean.getQuantite().setValue(Double.valueOf(0));
			bean.getPrixUnitaireAchat().setValue(BigDecimal.ZERO);
			bean.getPrixUnitaireVente().setValue(BigDecimal.ZERO);
			
			operation.createInventaire(ctx, bean);
		
			var msgTpl = "Les variantes sélectionnées de l'article %s ont été ajoutées au magasin %s avec succès!";
			ctx.success(String.format(msgTpl, articleCode, magasinCode));
		}		
	}
	
	public InventaireBean save(ClientContext ctx, InventaireBean bean) {
		switch (bean.getAction()) {
		case ACT01:
			ajusterQuantite(ctx, bean);
			break;
			
		case ACT02:
			modifierPrixVente(ctx, bean);
			break;
			
		case ACT03:
			modifierUniteVente(ctx, bean);
			break;
			
		case ACT04:
			modifierUniteAchat(ctx, bean);
			
			break;

		default:
			break;
		}
		
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();
		return operation.findInventaireByCode(ctx, magasinCode, articleCode, variantCode);	
	}

	public BigDecimal getPrixUnitaireAchat(ClientContext ctx, String magasinCode, String articleCode, String variantCode, String uniteCode) {
		return operation.getPrixUnitaireAchat(ctx, magasinCode, articleCode, variantCode, uniteCode);
	}
	
	public BigDecimal getPrixUnitaireVente(ClientContext ctx, String magasinCode, String articleCode, String variantCode, String uniteCode) {
		return operation.getPrixUnitaireVente(ctx, magasinCode, articleCode, variantCode, uniteCode);
	}
	
	public void importInventory(ClientContext ctx, List<InputStream> excelsData) {
		Consumer<InputStream> _import = e -> importInventaireOperation.importInventory(ctx, e);
		excelsData.stream().forEach(_import);
	}
	
	private void ajusterQuantite(ClientContext ctx, InventaireBean bean) {
		serviceHelper.ajusterQuantite(ctx, bean);
	}

	private void modifierUniteAchat(ClientContext ctx, InventaireBean bean) {
		operation.updateInventaireUniteAchat(ctx, bean);
	}

	private void modifierUniteVente(ClientContext ctx, InventaireBean bean) {
		operation.updateInventaireUniteVente(ctx, bean);
	}

	private void modifierPrixVente(ClientContext ctx, InventaireBean bean) {
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();
		
		var freshBean = operation.findInventaireByCode(ctx, magasinCode, articleCode, variantCode);
		
		freshBean.getPrixUnitaireVente().setValue(bean.getPrixUnitaireVenteAjust().getValue());
		operation.updateInventairePrixVente(ctx, freshBean);
		
		var prix = freshBean.getPrixUnitaireVente().getValue();
		var msgTpl = "Le prix de la variante <strong>%s</strong> de l'article <strong>%s</strong> a été ajusté au magasin <strong>%s</strong> avec succès!"
				+ " Le nouveau prix de vente est <strong>%,.2f</strong>";
		ctx.success(String.format(msgTpl, variantCode, articleCode, magasinCode, prix.doubleValue()));
	}

	
}
