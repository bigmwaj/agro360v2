package com.agro360.bo.bean.finance;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TaxeBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> taxeCode = new FieldMetadata<>("Taxe");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<Double> taux = new FieldMetadata<>("Taux");

	private FieldMetadata<String> deleteBtn = new FieldMetadata<>();
}
