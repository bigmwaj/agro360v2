package com.agro360.service.rule.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TestBean extends AbstractBean {

	private static final long serialVersionUID = 3268088477341573098L;
	
	private final FieldMetadata<String> stringField = new FieldMetadata<>();
	
	private final FieldMetadata<Integer> intField = new FieldMetadata<>();
}
