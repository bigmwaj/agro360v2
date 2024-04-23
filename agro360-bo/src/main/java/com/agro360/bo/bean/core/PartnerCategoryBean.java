package com.agro360.bo.bean.core;

import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class PartnerCategoryBean extends AbstractBean{

	private static final long serialVersionUID = 8023125234394845154L;

	private FieldMetadata<String> categoryCode = new FieldMetadata<>("Code");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<Boolean> selected = new FieldMetadata<>("Sélectionné?");

	private List<PartnerCategoryBean> children = new ArrayList<>();

}
