package com.agro360.service.mapper.common;

import java.util.function.Consumer;

import com.agro360.service.metadata.FieldMetadata;

public class AbstractMapper {

	protected <T> void setDtoValue(Consumer<T> setter, FieldMetadata<T> field) {
		if (field.isEditable()) {
			setter.accept(field.getValue());
		}
	}
}
