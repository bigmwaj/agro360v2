package com.agro360.service.mapper.common;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.agro360.service.metadata.FieldMetadata;

public class AbstractMapper {

	protected <T> void setDtoValue(Consumer<T> setter, FieldMetadata<T> field) {
		if (field.isEditable()) {
			setter.accept(field.getValue());
		}
	}
	
	protected <V> void setDtoValue(Consumer<V> setter, FieldMetadata<?> field, V value) {
		if (field.isEditable()) {
			setter.accept(value);
		}
	}

	protected <T>void setMap(Consumer<Map<Object, String>> field, T[] values, Function<T, String> libelle) {
		var options = Arrays.stream(values).collect(Collectors.toMap( e -> Object.class.cast(e), libelle));
		field.accept(options);
	}

}
