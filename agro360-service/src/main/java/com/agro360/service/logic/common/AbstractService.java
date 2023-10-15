package com.agro360.service.logic.common;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.dao.common.IDao;
import com.agro360.dto.common.AbstractDto;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;
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

	/**
	 * 
	 * @param <B>
	 * @param bean
	 * @param operation
	 * @return
	 */
	protected <B extends AbstractBean> B applyInitRules(B bean, String operation) {
		var search = "init-search-form".equals(operation);
		if( search ) {
			return bean;
		}
		var beanCtx = new BeanContext();
		beanCtx.setOperation(operation);
		beanCtx.setRuleName(getRulePath());
		
		new RuleXmlHelper().applyRules(beanCtx, bean);
		
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

	/**
	 * 
	 * @param <B>
	 * @param bean
	 * @return
	 */
	protected <B extends AbstractBean> B applyValidationRules(B bean) {
		var operation = bean.getOperation();
		if( operation == null ) {
			throw new RuntimeException("Opéraiton initiale inconnue!");
		}
		
		applyInitRules(bean, operation);
		
		var beanCtx = new BeanContext();
		beanCtx.setOperation("validation-form");
		beanCtx.setRuleName(getRulePath());
		
		new RuleXmlHelper().applyRules(beanCtx, bean);
		
		return bean;
	}

	protected abstract String getRulePath();
}
