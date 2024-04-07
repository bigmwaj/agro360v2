package com.agro360.bo.bean.stock;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UniteBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<String> uniteCode = new FieldMetadata<>("Unité");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");
}
