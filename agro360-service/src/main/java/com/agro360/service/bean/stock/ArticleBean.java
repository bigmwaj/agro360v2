package com.agro360.service.bean.stock;

import java.util.ArrayList;
import java.util.List;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.TypeArticleEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ArticleBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> articleCode = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<TypeArticleEnumVd> typeArticle = new FieldMetadata<>();

	private UniteBean unite = new UniteBean();
	
	private List<VariantBean> variants = new ArrayList<>();
	
	private List<ConversionBean> conversions = new ArrayList<>();

	public void setUnite(UniteBean unite) {
		this.unite = unite;
	}
}
