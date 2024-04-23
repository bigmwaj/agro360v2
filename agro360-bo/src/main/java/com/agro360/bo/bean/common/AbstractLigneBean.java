package com.agro360.bo.bean.common;

import java.math.BigDecimal;
import java.util.Objects;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.vd.av.LigneTypeEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractLigneBean<E extends AbstractLigneDto> extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> ligneId = new FieldMetadata<>("ID");

	private FieldMetadata<Integer> numero = new FieldMetadata<>("Numéro");

	private FieldMetadata<LigneTypeEnumVd> typeLigne = new FieldMetadata<>("Type");

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
		setAction(ClientOperationEnumVd.CREATE);
	}

	public void setArticle(ArticleBean article) {
		Objects.requireNonNull(article);
		this.article = article;
	}
}
