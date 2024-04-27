package com.agro360.operation.logic.common;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.dao.common.IDao;
import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.EntityManager;

public abstract class AbstractOperation<E extends AbstractDto, K> {

	protected abstract IDao<E, K> getDao();
	
	@Autowired
	protected EntityManager entityManager;
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
	
	protected <T> void setDtoValue(Consumer<T> setter, FieldMetadata<T> field) {
		setter.accept(field.getValue());
	}
	
	protected <T> void setDtoChangedValue(Consumer<T> setter, FieldMetadata<T> field) {
		if (field.isValueChanged()) {
			setter.accept(field.getValue());
		}
	}

	public E save(E dto) {
		dto.setCreateBy("A.MOUAFO");
		dto.setCreateAt(LocalDateTime.now());
		dto.setUpdateBy("A.MOUAFO");
		dto.setUpdateAt(LocalDateTime.now());
		return getDao().save(dto);
	}

	public void delete(E dto) {
		getDao().delete(dto);
	}
	
	protected String getNullOrUpperCase(String value) {
		if( value != null && !value.isBlank() ) {
			return value.toUpperCase();
		}
		
		return null;
	}
	
	protected String getNullOrUpperCase(Supplier<FieldMetadata<String>> valueFunction) {
		return getNullOrUpperCase(valueFunction.get().getValue());
	}
}
