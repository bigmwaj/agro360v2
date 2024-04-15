package com.agro360.bo.bean.av;

import java.math.BigDecimal;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.bo.utils.TypeScriptInfos;
import com.agro360.vd.av.LigneTypeEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class LigneBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<Long> ligneId = new FieldMetadata<>("ID");

	private FieldMetadata<LigneTypeEnumVd> type = new FieldMetadata<>("Type", LigneTypeEnumVd.getAsMap());

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<Double> quantite = new FieldMetadata<>("Quantité");

	private FieldMetadata<BigDecimal> prixUnitaire = new FieldMetadata<>("Prix Unitaire");

	private FieldMetadata<BigDecimal> prixTotal = new FieldMetadata<>("Prix Total");
	
	private FieldMetadata<BigDecimal> prixTotalHT = new FieldMetadata<>("Prix Total(HT)");
	
	private FieldMetadata<BigDecimal> prixTotalTTC = new FieldMetadata<>("Prix Total(TTC)");
	
	private FieldMetadata<BigDecimal> taxe = new FieldMetadata<>("Taxe");
	
	private FieldMetadata<String> variantCode = new FieldMetadata<>("Variant");
	
	private FieldMetadata<RemiseTypeEnumVd> remiseType = new FieldMetadata<>("Type Remise", RemiseTypeEnumVd.getAsMap());
	
	private FieldMetadata<Double> remiseTaux = new FieldMetadata<>("Taux Remise");
	
	private FieldMetadata<BigDecimal> remiseMontant = new FieldMetadata<>("Montant Remise");
	
	private FieldMetadata<String> remiseRaison = new FieldMetadata<>("Raison Remise");

	@Setter
	private UniteBean unite = new UniteBean();

	@Setter
	private ArticleBean article = new ArticleBean();
}
