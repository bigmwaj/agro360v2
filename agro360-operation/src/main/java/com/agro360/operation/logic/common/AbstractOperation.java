package com.agro360.operation.logic.common;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.dao.common.IDao;
import com.agro360.dto.common.AbstractDto;
import com.agro360.operation.context.ClientContext;

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

	protected E save(ClientContext ctx, E dto) {
		dto.setCreateBy(ctx.getCurrentUser());
		dto.setCreateAt(LocalDateTime.now());
		dto.setUpdateBy(ctx.getCurrentUser());
		dto.setUpdateAt(LocalDateTime.now());
		return getDao().save(dto);
	}

	protected void delete(ClientContext ctx, E dto) {
		getDao().delete(dto);
	}
	
	protected String getNullOrUpperCase(String value) {
		return getNullOrUpperCase(value, "");
	}
	
	protected String getNullOrUpperCase(String value, @NonNull String wrapper) {
		if( value != null && !value.isBlank() ) {
			return wrapper + value.toUpperCase() + wrapper;
		}
		
		return null;
	}
	
	protected String getNullOrUpperCase(Supplier<FieldMetadata<String>> valueFunction) {
		return getNullOrUpperCase(valueFunction, "");
	}
	
	protected String getNullOrUpperCase(Supplier<FieldMetadata<String>> valueFunction, String wrapper) {
		return getNullOrUpperCase(valueFunction.get().getValue(), wrapper);
	}
}
