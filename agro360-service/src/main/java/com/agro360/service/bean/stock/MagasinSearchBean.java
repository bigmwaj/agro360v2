package com.agro360.service.bean.stock;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class MagasinSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> magasinCode = new FieldMetadata<>("Magasin");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");
	
}
