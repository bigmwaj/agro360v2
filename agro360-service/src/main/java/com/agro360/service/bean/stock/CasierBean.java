package com.agro360.service.bean.stock;

import com.agro360.service.bean.common.AbstractFormBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CasierBean extends AbstractFormBean {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<String> casierCode = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();
}
