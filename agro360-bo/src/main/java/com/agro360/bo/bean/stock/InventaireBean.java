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

	private FieldMetadata<BigDecimal> prixUnitaireAchat = new FieldMetadata<>("Prix Achat");

	private FieldMetadata<BigDecimal> prixUnitaireVente = new FieldMetadata<>("Prix Vente");

	private FieldMetadata<Double> quantite = new FieldMetadata<>("Quantité");

	@Setter
	private MagasinBean magasin = new MagasinBean();

	@Setter
	private ArticleBean article = new ArticleBean();
}
