package com.agro360.operation.metadata;

import java.util.List;

import lombok.Data;

@Data
public class AttributeMetadataConfig<T> {

	private T value;
	
	private List<ConstraintConfig> constraints;
}
