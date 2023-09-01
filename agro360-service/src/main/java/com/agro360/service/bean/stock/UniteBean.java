package com.agro360.service.bean.stock;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UniteBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<String> uniteCode = new FieldMetadata<>("Code");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");
}
