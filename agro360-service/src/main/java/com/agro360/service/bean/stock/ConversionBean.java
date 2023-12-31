package com.agro360.service.bean.stock;

import java.util.Objects;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ConversionBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Double> facteur = new FieldMetadata<>("Facteur");
	
	private UniteBean unite = new UniteBean();
	
	public void setUnite(UniteBean unite) {
		Objects.requireNonNull(unite);
		this.unite = unite;
	}
}
