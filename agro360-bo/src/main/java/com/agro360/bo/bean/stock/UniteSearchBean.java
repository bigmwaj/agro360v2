package com.agro360.bo.bean.stock;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UniteSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> uniteCode = new FieldMetadata<>("Code");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

}
