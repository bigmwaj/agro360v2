package com.agro360.operation.logic.common;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.dao.common.IDao;
import com.agro360.dto.common.AbstractDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.helper.RuleXmlHelper;
import com.agro360.vd.common.ClientOperationEnumVd;

public abstract class AbstractOperation<E extends AbstractDto, K> {

	protected final static String MESSAGES_MODEL_KEY = "messages";

	protected final static String ID_MODEL_KEY = "id";
	
	protected IDao<E, K> dao;

	protected abstract IDao<E, K> getDao();
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
	
	protected <T> void setDtoValue(Consumer<T> setter, FieldMetadata<T> field) {
		setter.accept(field.getValue());
	}
	
	protected <V> void setDtoValue(Consumer<V> setter, FieldMetadata<?> field, V value) {
		setter.accept(value);
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

	protected <B extends AbstractBean> B applyInitRules(B bean, ClientOperationEnumVd operation) {
		var helper = new RuleXmlHelper();
		var userCtx = new ClientContext();
		helper.applyInitRules(userCtx, bean, getRulePath(), operation);
		bean.setOperation(operation);
		return bean;
	}
	
	protected <B extends AbstractBean> B applyInitChangeStatusRules(B bean) {
		return applyInitRules(bean, ClientOperationEnumVd.INIT_CHANGE_STATUS_FORM);
	}
	
	protected <B extends AbstractBean> B applyInitEditRules(B bean) {
		return applyInitRules(bean, ClientOperationEnumVd.INIT_UPDATE_FORM);
	}
	
	protected <B extends AbstractBean> B applyInitDeleteRules(B bean) {
		return applyInitRules(bean, ClientOperationEnumVd.INIT_DELETE_FORM);
	}
	
	protected <B extends AbstractBean> B applyInitCreateRules(B bean) {
		return applyInitRules(bean, ClientOperationEnumVd.INIT_CREATE_FORM);
	}
	
	protected <B extends AbstractBean> B applyInitSearchRules(B bean) {
		return applyInitRules(bean, ClientOperationEnumVd.INIT_SEARCH_FORM);
	}

	protected <B extends AbstractBean> B applyValidationRules(B bean) {
		var operation = bean.getOperation();
		if( operation == null ) {
			throw new RuntimeException("Opéraiton initiale inconnue!");
		}
		var helper = new RuleXmlHelper();
		var userCtx = new ClientContext();
		
		helper.applyValidationRules(userCtx, bean, getRulePath(), operation);
		
		return bean;
	}

	protected abstract String getRulePath();
}
