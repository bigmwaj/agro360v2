package com.agro360.bo.bean.finance;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CompteBean extends AbstractBean{

	private static final long serialVersionUID = 1969867939120937699L;

	private FieldMetadata<String> compteCode = new FieldMetadata<>("Compte");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<String> deleteBtn = new FieldMetadata<>();

}
