package com.agro360.bo.bean.stock;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ConversionBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Double> facteur = new FieldMetadata<>("Facteur");

	@Setter
	private UniteBean unite = new UniteBean();
}
