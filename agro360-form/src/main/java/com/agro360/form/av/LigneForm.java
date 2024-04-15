package com.agro360.form.av;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dto.av.LigneDto;
import com.agro360.form.common.AbstractForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleOperation;
import com.agro360.operation.logic.stock.InventaireOperation;
import com.agro360.operation.logic.stock.VariantOperation;
import com.agro360.vd.av.LigneTypeEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class LigneForm extends AbstractForm{
	
	@Autowired
	private VariantOperation variantOperation;	
	
	@Autowired
	private ArticleOperation articleOperation;	
	
	@Autowired
	private InventaireOperation inventaireOperation;	

	public LigneBean initCreateFormBean(ClientContext ctx, 
			String commandeCode, 
			Optional<String> magasinCode, 
			Optional<String> alias) {

		var variant = alias.map(e -> variantOperation.findVariantByAlias(ctx, e)).orElse(null);
		
		var bean = AchatVenteMapper.map(new LigneDto());

		bean.getPrixUnitaire().setValue(BigDecimal.ZERO);
		if( variant != null ) {
			var variantCode = variant.getVariantCode().getValue();
			var articleCode = variant.getArticleCode().getValue();	
			
			var article = articleOperation.findArticleByCode(ctx, articleCode);
			
			bean.getArticle().getArticleCode().setValue(articleCode);
			bean.getType().setValue(LigneTypeEnumVd.ARTC);
			bean.getVariantCode().setValue(variantCode);
			bean.setUnite(article.getUnite());
			
			if( magasinCode.isPresent() ) {
				initFromInventaire(ctx, bean, magasinCode.get());
			}			
		}
		
		bean.setAction(EditActionEnumVd.CREATE);
		
		bean.getType().setRequired(true);
		
		bean.getQuantite().setRequired(true);
		bean.getQuantite().setValue(1.0);
		bean.getRemiseMontant().setValue(BigDecimal.ZERO);
		bean.getRemiseType().setValue(RemiseTypeEnumVd.MONTANT);
		
		bean.getPrixTotal().setEditable(false);
		bean.getTaxe().setEditable(false);
		bean.getPrixTotalHT().setEditable(false);
		bean.getPrixTotalTTC().setEditable(false);
		return bean;
	}
	
	LigneBean initUpdateFormBean(ClientContext ctx, CommandeBean commande, LigneBean bean) {
		
		bean.setAction(EditActionEnumVd.UPDATE);
		
		bean.getType().setRequired(true);
		
		bean.getQuantite().setRequired(true);
		
		bean.getPrixTotal().setEditable(false);
		bean.getTaxe().setEditable(false);
		bean.getPrixTotalHT().setEditable(false);
		bean.getPrixTotalTTC().setEditable(false); 
		return bean;
	}
	
	private void initFromInventaire(ClientContext ctx, LigneBean bean, String magasinCode) {
		var variantCode = bean.getVariantCode().getValue();
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var inv = inventaireOperation.findInventaireByCode(ctx, magasinCode, articleCode, variantCode);				
		if( inv != null ) {
			var pu = inv.getPrixUnitaireVente().getValue();
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

}
