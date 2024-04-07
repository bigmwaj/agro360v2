package com.agro360.bo.bean.core;

import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CategoryBean extends AbstractBean{

	private static final long serialVersionUID = 4953208601044344467L;
	
	private FieldMetadata<String> parentCategoryCode = new FieldMetadata<>("Parent");
	
	private FieldMetadata<String> categoryCode = new FieldMetadata<>("Code");
	
	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private List<CategoryBean> children = new ArrayList<>();
}
