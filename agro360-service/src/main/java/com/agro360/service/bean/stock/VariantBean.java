package com.agro360.service.bean.stock;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class VariantBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<String> variantCode = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();
}
