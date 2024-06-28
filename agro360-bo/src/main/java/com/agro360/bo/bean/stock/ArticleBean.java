package com.agro360.bo.bean.stock;

import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.common.IAssignmentBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.stock.ArticleTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ArticleBean extends AbstractBean implements IAssignmentBean{

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> articleCode = new FieldMetadata<>("Article");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<String> origine = new FieldMetadata<>("Orgine");

	private FieldMetadata<ArticleTypeEnumVd> type = new FieldMetadata<>("Type", ArticleTypeEnumVd.getAsMap());

	@Setter
	private UniteBean unite = new UniteBean();

	@Setter
	private PartnerBean fabriquant = new PartnerBean();

	@Setter
	private PartnerBean distributeur = new PartnerBean();

	@Setter
	private ArticleCategoryBean categoriesHierarchie = new ArticleCategoryBean();

	private List<VariantBean> variants = new ArrayList<>();

	private List<ConversionBean> conversions = new ArrayList<>();

	private List<ArticleTaxeBean> taxes = new ArrayList<>();

	@Override
	public String getAssigneeCode() {
		return articleCode.getValue();
	}
	
	
}
