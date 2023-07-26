package com.agro360.service.bean.tiers;

import java.util.ArrayList;
import java.util.List;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CategoryBean extends AbstractEditFormBean{

	private static final long serialVersionUID = 4953208601044344467L;
	
	private FieldMetadata<String> categoryCode = new FieldMetadata<>();
	
	private FieldMetadata<String> description = new FieldMetadata<>();

	private List<CategoryBean> children = new ArrayList<>();
}
