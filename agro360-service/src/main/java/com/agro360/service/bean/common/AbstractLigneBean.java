package com.agro360.service.bean.common;

import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.bean.stock.VariantBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.TypeLigneEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractLigneBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> lineId = new FieldMetadata<>();

	private FieldMetadata<Integer> numero = new FieldMetadata<>();

	private UniteBean unite = new UniteBean();

	private ArticleBean article = new ArticleBean();

	private VariantBean variant = new VariantBean();

	private FieldMetadata<TypeLigneEnumVd> typeLigne = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<Double> quantite = new FieldMetadata<>();

	private FieldMetadata<Double> prixUnitaire = new FieldMetadata<>();
}
