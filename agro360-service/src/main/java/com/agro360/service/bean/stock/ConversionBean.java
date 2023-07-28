package com.agro360.service.bean.stock;

import java.util.Objects;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ConversionBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Double> facteur = new FieldMetadata<>();
	
	private UniteBean unite = new UniteBean();
	
	public void setUnite(UniteBean unite) {
		Objects.requireNonNull(unite);
		this.unite = unite;
	}
}
