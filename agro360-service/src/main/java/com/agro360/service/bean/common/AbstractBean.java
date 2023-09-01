package com.agro360.service.bean.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.common.EditActionEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -8531682957497502966L;
	
	private final String __TYPE__ = "BEAN";

	private boolean valueChanged;

	private EditActionEnumVd action = EditActionEnumVd.SYNC;

	public static final Consumer<AbstractBean> setActionToCreate = b -> b.setAction(EditActionEnumVd.CREATE);
	
	protected <T>Map<Object, String> getOptionsMap(T[] values, Function<T, String> libelle) {
		return Arrays.stream(values).collect(Collectors.toMap( e -> Object.class.cast(e), libelle));
	}

	private String getGetterName(String fieldName) {
		return "get" + ((fieldName.charAt(0) + "").toUpperCase()) + fieldName.substring(1);
	}

	@SuppressWarnings("unchecked")
	public <T> FieldMetadata<T> getField(String fieldName) {
		try {
			return (FieldMetadata<T>) getClass().getMethod(getGetterName(fieldName)).invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
