package com.agro360.bo.bean.stock;

import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.stock.ArticleTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ArticleBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> articleCode = new FieldMetadata<>("Article");

	private FieldMetadata<String> description = new FieldMetadata<>("Libellé");

	private FieldMetadata<ArticleTypeEnumVd> type = new FieldMetadata<>("Type", ArticleTypeEnumVd.getAsMap());

	private UniteBean unite = new UniteBean();
	
	private List<VariantBean> variants = new ArrayList<>();
	
	private List<ConversionBean> conversions = new ArrayList<>();

	public void setUnite(UniteBean unite) {
		this.unite = unite;
	}
}
