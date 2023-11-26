package com.agro360.service.logic.common;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.dao.common.IDao;
import com.agro360.dto.common.AbstractDto;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.UserContext;
import com.agro360.service.rule.helper.RuleXmlHelper;

public abstract class AbstractService<E extends AbstractDto, K> {

	protected final static String MESSAGES_MODEL_KEY = "messages";

	protected final static String ID_MODEL_KEY = "id";

	protected IDao<E, K> dao;

	protected abstract IDao<E, K> getDao();
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
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

	protected <B extends AbstractBean> B applyInitRules(B bean, String operation) {
		var helper = new RuleXmlHelper();
		var userCtx = new UserContext();
		helper.applyInitRules(userCtx, bean, getRulePath(), operation);
		bean.setOperation(operation);
		return bean;
	}
	
	protected <B extends AbstractBean> B applyInitEditRules(B bean) {
		return applyInitRules(bean, "init-edit-form");
	}
	
	protected <B extends AbstractBean> B applyInitDeleteRules(B bean) {
		return applyInitRules(bean, "init-delete-form");
	}
	
	protected <B extends AbstractBean> B applyInitCreateRules(B bean) {
		return applyInitRules(bean, "init-create-form");
	}
	
	protected <B extends AbstractBean> B applyInitSearchRules(B bean) {
		return applyInitRules(bean, "init-search-form");
	}

	protected <B extends AbstractBean> B applyValidationRules(B bean) {
		var operation = bean.getOperation();
		if( operation == null ) {
			throw new RuntimeException("Opéraiton initiale inconnue!");
		}
		var helper = new RuleXmlHelper();
		var userCtx = new UserContext();
		
		helper.applyValidationRules(userCtx, bean, getRulePath(), operation);
		
		return bean;
	}

	protected abstract String getRulePath();
}
