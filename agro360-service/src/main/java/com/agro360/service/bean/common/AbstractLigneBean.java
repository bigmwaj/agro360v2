package com.agro360.service.bean.common;

import java.util.Objects;

import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.bean.stock.VariantBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.TypeLigneEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractLigneBean<E extends AbstractLigneDto> extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> ligneId = new FieldMetadata<>();

	private FieldMetadata<Integer> numero = new FieldMetadata<>();

	private UniteBean unite = new UniteBean();

	private ArticleBean article = new ArticleBean();

	private VariantBean variant = new VariantBean();

	private FieldMetadata<TypeLigneEnumVd> typeLigne = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<Double> quantite = new FieldMetadata<>();

	private FieldMetadata<Double> prixUnitaire = new FieldMetadata<>();
	
	public void setUnite(UniteBean unite) {
		Objects.requireNonNull(unite);
		this.unite = unite;
	}
	
	public void setArticle(ArticleBean article) {
		Objects.requireNonNull(article);
		this.article = article;
	}
	
	public void setVariant(VariantBean variant) {
		Objects.requireNonNull(variant);
		this.variant = variant;
	}
}
