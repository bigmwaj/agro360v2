package com.agro360.service.bean.common;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import com.agro360.service.metadata.FieldMetadata;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -8531682957497502966L;

	private String getGetterName(String fieldName) {
		return "get" + ((fieldName.charAt(0) + "").toUpperCase()) + fieldName.substring(1);
	}

	@SuppressWarnings("unchecked")
	public <T> FieldMetadata<T> getField(String fieldName) {
		try {
			return (FieldMetadata<T>) getClass().getMethod(getGetterName(fieldName)).invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
