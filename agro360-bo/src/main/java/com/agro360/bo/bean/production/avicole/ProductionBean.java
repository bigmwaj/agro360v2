package com.agro360.bo.bean.production.avicole;

import java.util.Objects;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.bo.utils.TypeScriptInfos;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.vd.production.avicole.ProductionCategoryEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class ProductionBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> numeroJournee = new FieldMetadata<>("#Journée");

	private FieldMetadata<Long> productionId = new FieldMetadata<>("ID");

	private FieldMetadata<String> commentaire = new FieldMetadata<>("Commentaire");

	private FieldMetadata<Double> quantite = new FieldMetadata<>("Quantité");

	private FieldMetadata<Double> alveole = new FieldMetadata<>("Alvéole");

	private VariantBean variant = new VariantBean();

	private FieldMetadata<ProductionCategoryEnumVd> category = new FieldMetadata<>("Catégrie");

	private UniteBean unite = new UniteBean();

	private FieldMetadata<String> uniteLibelle = new FieldMetadata<>("Unite");

	private ArticleBean article = new ArticleBean();

	public void setUnite(UniteBean unite) {
		Objects.requireNonNull(unite);
		this.unite = unite;
	}

	public void setVariant(VariantBean variant) {
		Objects.requireNonNull(variant);
		this.variant = variant;
	}

	public void initForCreateForm() {
		productionId.setValue(null);
		setAction(ClientOperationEnumVd.CREATE);
	}

	public void setArticle(ArticleBean article) {
		Objects.requireNonNull(article);
		this.article = article;
	}
}
