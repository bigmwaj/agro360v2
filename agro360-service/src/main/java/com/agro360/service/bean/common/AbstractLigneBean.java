package com.agro360.service.bean.common;

import java.math.BigDecimal;
import java.util.Objects;

import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.stock.TypeLigneEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractLigneBean<E extends AbstractLigneDto> extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> ligneId = new FieldMetadata<>("ID");

	private FieldMetadata<Integer> numero = new FieldMetadata<>("Numéro");

	private FieldMetadata<TypeLigneEnumVd> typeLigne = new FieldMetadata<>("Type");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<Double> quantite = new FieldMetadata<>("Quantité");

	private FieldMetadata<BigDecimal> prixUnitaire = new FieldMetadata<>("Prix Unitaire");

	private FieldMetadata<BigDecimal> prixTotal = new FieldMetadata<>("Prix Total");
	
	private FieldMetadata<String> variantCode = new FieldMetadata<>("Variant");

	private UniteBean unite = new UniteBean();

	private ArticleBean article = new ArticleBean();
	
	public void setUnite(UniteBean unite) {
		Objects.requireNonNull(unite);
		this.unite = unite;
	}
	
	public void initForCreateForm() {
		ligneId.setValue(null);
		setAction(EditActionEnumVd.CREATE);
	}
	
	public void setArticle(ArticleBean article) {
		Objects.requireNonNull(article);
		this.article = article;
	}
}
