package com.agro360.bo.bean.av;

import java.math.BigDecimal;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.bo.utils.TypeScriptInfos;
import com.agro360.vd.av.LigneTypeEnumVd;
import com.agro360.vd.common.EditActionEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class LigneBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<Long> ligneId = new FieldMetadata<>("ID");

	private FieldMetadata<Integer> numero = new FieldMetadata<>("Numéro");

	private FieldMetadata<LigneTypeEnumVd> type = new FieldMetadata<>("Type", LigneTypeEnumVd.getAsMap());

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<Double> quantite = new FieldMetadata<>("Quantité");

	private FieldMetadata<BigDecimal> prixUnitaire = new FieldMetadata<>("Prix Unitaire");

	private FieldMetadata<BigDecimal> prixTotal = new FieldMetadata<>("Prix Total");
	
	private FieldMetadata<String> variantCode = new FieldMetadata<>("Variant");

	@Setter
	private UniteBean unite = new UniteBean();

	@Setter
	private ArticleBean article = new ArticleBean();

	public void initForCreateForm() {
		ligneId.setValue(null);
		setAction(EditActionEnumVd.CREATE);
	}
}
