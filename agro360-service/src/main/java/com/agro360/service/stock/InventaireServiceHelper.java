package com.agro360.service.stock;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.OperationBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.stock.InventaireOperation;
import com.agro360.operation.logic.stock.OperationOperation;
import com.agro360.vd.av.LigneTypeEnumVd;
import com.agro360.vd.stock.OperationTypeEnumVd;

@Service
public class InventaireServiceHelper {
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	@Autowired
	private InventaireOperation operation;
	
	@Autowired
	private OperationOperation operationOperation;
	
	@Autowired
	private LigneOperation ligneOperation;

	public void ajusterQuantite(ClientContext ctx, InventaireBean bean) {
		
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();

		createOperation(ctx, magasinCode, articleCode, variantCode, bean.getQuantiteAjust().getValue(), OperationTypeEnumVd.ADJT);
		
	}
	
	public void enregistrerSortiesMagasin(ClientContext ctx, CommandeBean bean) {
		var commandeCode = bean.getCommandeCode().getValue();
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		
		var lignes = ligneOperation.findLignesCommande(ctx, commandeCode);
		
		for (var ligne : lignes) {			
			if( !LigneTypeEnumVd.ARTC.equals(ligne.getType().getValue()) ) {
				continue;
			}
			var articleCode = ligne.getArticle().getArticleCode().getValue();
			var variantCode = ligne.getVariantCode().getValue();
			
			createOperation(ctx, magasinCode, articleCode, variantCode,
					ligne.getQuantite().getValue(), OperationTypeEnumVd.SORT);
		}
		
	}
	
	private void createOperation(ClientContext ctx, String magasinCode, String articleCode,
			String variantCode, Double quantite, OperationTypeEnumVd type) {
		var freshBean = operation.findInventaireByCode(ctx, magasinCode, articleCode, variantCode);
		var qte = quantite;
		if( OperationTypeEnumVd.SORT.equals(type)) {
			qte = qte * -1.0;
		}
		var op = new OperationBean();
		op.getMagasinCode().setValue(magasinCode);
		op.getArticleCode().setValue(articleCode);
		op.getVariantCode().setValue(variantCode);
		op.getQuantite().setValue(quantite);
		op.getInventaireAvant().setValue(freshBean.getQuantite().getValue());
		op.getType().setValue(type);
		op.getDate().setValue(LocalDateTime.now());
		
		freshBean.getQuantite().setValue(freshBean.getQuantite().getValue() + qte);
		freshBean.getQuantite().setValueChanged(true);
		
		operationOperation.createOperation(ctx, op);
		
		operation.updateInventaireQuantite(ctx, freshBean);
		qte = freshBean.getQuantite().getValue();
		
		var msgTpl = "La quantité de la variante <strong>%s</strong> de l'article <strong>%s</strong> a été ajustée au magasin <strong>%s</strong> avec succès!"
				+ " La nouvelle quantité en stock est <strong>%,.2f</strong>";
		ctx.success(String.format(msgTpl, variantCode, articleCode, magasinCode, qte.doubleValue()));
	}

}
