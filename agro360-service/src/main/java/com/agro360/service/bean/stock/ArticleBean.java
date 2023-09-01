package com.agro360.service.bean.stock;

import java.util.ArrayList;
import java.util.List;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.TypeArticleEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ArticleBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> articleCode = new FieldMetadata<>("Article");

	private FieldMetadata<String> description = new FieldMetadata<>("Libellé");

	private FieldMetadata<TypeArticleEnumVd> typeArticle = new FieldMetadata<>("Type");

	private UniteBean unite = new UniteBean();
	
	private List<VariantBean> variants = new ArrayList<>();
	
	private List<ConversionBean> conversions = new ArrayList<>();

	public void setUnite(UniteBean unite) {
		this.unite = unite;
	}
}
