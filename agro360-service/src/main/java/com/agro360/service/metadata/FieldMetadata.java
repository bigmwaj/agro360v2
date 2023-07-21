package com.agro360.service.metadata;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FieldMetadata<T> {

	private T value;
	
	private boolean required;
	
	private boolean editable;
	
	private Integer maxLength;
	
	private Double max;
	
	private Double min;
	
	private Map<String, Object> valueOptions;
	
	private String tooltip;
	
	private String icon;

	@Override
	public String toString() {
		return "FieldMetadata [value=" + value + "]";
	}
	
	
}
