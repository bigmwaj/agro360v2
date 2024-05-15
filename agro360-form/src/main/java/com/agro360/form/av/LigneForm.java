package com.agro360.form.av;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleOperation;
import com.agro360.operation.logic.stock.InventaireOperation;
import com.agro360.operation.logic.stock.VariantOperation;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.av.LigneTypeEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class LigneForm extends AbstractForm{
	
	@Autowired
	private VariantOperation variantOperation;	
	
	@Autowired
	private ArticleOperation articleOperation;	
	
	@Autowired
	private InventaireOperation inventaireOperation;	

	@MetadataBeanName("av/ligne")
	public LigneBean initCreateFormBean(ClientContext ctx, 
			CommandeTypeEnumVd type,
			String commandeCode, 
			String magasinCode, 
			Optional<String> alias) {

		var variant = alias.map(e -> variantOperation.findVariantByAlias(ctx, e)).orElse(null);
		
		var bean = new LigneBean();

		bean.getPrixUnitaire().setValue(BigDecimal.ZERO);
		if( variant != null ) {
			var variantCode = variant.getVariantCode().getValue();
			var articleCode = variant.getArticleCode().getValue();	
			
			var article = articleOperation.findArticleByCode(ctx, articleCode);
			var typeArticle = article.getType().getValue();
			switch (typeArticle) {
			case ARTC:
				bean.getType().setValue(LigneTypeEnumVd.ARTC);
				break;
				
			case SSTD:
				bean.getType().setValue(LigneTypeEnumVd.SSTD);
				break;

			default:
				var msgTpl = "Le type d'article %s n'est pas pris en charge";
				throw new RuntimeException(String.format(msgTpl, typeArticle));
			}
			bean.getArticle().getArticleCode().setValue(articleCode);
			bean.getVariantCode().setValue(variantCode);
			bean.setUnite(article.getUnite());
			
			initPrixUnitFromInventaire(ctx, bean, magasinCode);
			
			initArticle(ctx, bean);
		}
		
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getQuantite().setValue(1.0);
		bean.getRemiseMontant().setValue(BigDecimal.ZERO);
		bean.getRemiseType().setValue(RemiseTypeEnumVd.MONTANT);
		
		// TODO Gérer ceci proprement pour permettre au système de bien calculer les règles sur la ligne
		var commande = new CommandeBean();
		
		commande.setAction(ClientOperationEnumVd.CREATE);
		commande.getType().setValue(CommandeTypeEnumVd.VENTE);
		commande.getStatus().setValue(CommandeStatusEnumVd.BRLN);
		bean.setRootBean(commande);
		bean.setOwnerBean(commande);
		
		return bean;
	}
	
	private void initPrixUnitFromInventaire(ClientContext ctx, LigneBean bean, String magasinCode) {
		var variantCode = bean.getVariantCode().getValue();
		var articleCode = bean.getArticle().getArticleCode().getValue();
		
		var inv = inventaireOperation.findInventaireByCode(ctx, magasinCode, articleCode, variantCode);				
		if( inv != null ) {
			var pu = inv.getPrixUnitaireVente().getValue();
			bean.getUnite().getUniteCode().setValue(inv.getUniteVente().getUniteCode().getValue());
			if( !BigDecimal.ZERO.equals(pu) ) {
				bean.getPrixUnitaire().setValue(pu);
			}else {
				bean.getPrixUnitaire().setValue(BigDecimal.ZERO);
				var msgTpl = "Le prix de vente du variant <strong>%s</strong> de l'article <strong>%s</strong> "
						+ "n'est pas encore configuré dans le magasin <strong>%s</strong>. "
						+ "Veillez vérifier puis indiquer le prix de vente adéquat afin de faciliter la saisie.";
				ctx.warn(String.format(msgTpl, variantCode, articleCode, magasinCode));
			}
		}else {
			var msgTpl = "La variant <strong>%s</strong> de l'article <strong>%s</strong> n'est "
					+ "pas encore stockée dans le magasin <strong>%s</strong>. "
					+ "Veillez ajouter ce variant et configurer les prix de vente "
					+ "pour faciliter la saisie.";
			ctx.warn(String.format(msgTpl, variantCode, articleCode, magasinCode));
		}
	}
	
	void initArticle(ClientContext ctx, LigneBean ligne) {		
		var articleCode = ligne.getArticle().getArticleCode().getValue();
		var variantOptions = variantOperation.findVariantsByArticleCode(ctx, articleCode)
				.stream().map(StockMapper::asOption)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		ligne.getVariantCode().setValueOptions(variantOptions);
		
		var uniteOptions = articleOperation.findUnitesArticleByCode(ctx, articleCode)
				.stream().map(StockMapper::asOption)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		ligne.getUnite().getUniteCode().setValueOptions(uniteOptions);
		
	}
}
