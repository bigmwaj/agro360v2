package com.agro360.bo.bean.stock;

import java.math.BigDecimal;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class InventaireBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> variantCode = new FieldMetadata<>("Variant");
	
	private FieldMetadata<String> variantDescription = new FieldMetadata<>("Description");
	
	private FieldMetadata<String> alias = new FieldMetadata<>("Alias");

	private FieldMetadata<BigDecimal> prixUnitaireAchat = new FieldMetadata<>("Prix Achat");

	private FieldMetadata<BigDecimal> prixUnitaireVente = new FieldMetadata<>("Prix Vente");
	
	private FieldMetadata<BigDecimal> prixUnitaireVenteAjust = new FieldMetadata<>("Prix Vente");

	private FieldMetadata<Double> quantite = new FieldMetadata<>("Quantité");
	
	private FieldMetadata<Double> quantiteAjust = new FieldMetadata<>("Quantité");
	
	private FieldMetadata<String> ajustQteBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> defPrixVenteBtn = new FieldMetadata<>();

	@Setter
	private UniteBean uniteAchat = new UniteBean();

	@Setter
	private UniteBean uniteVente = new UniteBean();

	@Setter
	private MagasinBean magasin = new MagasinBean();

	@Setter
	private ArticleBean article = new ArticleBean();
}
