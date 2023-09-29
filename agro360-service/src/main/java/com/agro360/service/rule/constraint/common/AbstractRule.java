package com.agro360.service.rule.constraint.common;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.agro360.service.bean.common.AbstractBean;

public abstract class AbstractRule {

	protected final static String REQUIRED_NUMBER = "Un nombre est requis!";
	
	@Autowired
	private ApplicationContext appCtx;

	public abstract boolean apply(AbstractBean bean, String fieldName, Map<String, Object> params);

	public abstract boolean skipOnError();

	private Logger logger;
	
	protected AbstractRule getRuleByClassName(String className) {
		return (AbstractRule) appCtx.getBean(className);
	}

	public Logger getLogger() {
		return this.logger;
	}
	
	protected void setAppCtx(ApplicationContext appCtx) {
		this.appCtx = appCtx;
	}

}