package com.agro360.operation.rule.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

public abstract class AbstractRule<T extends AbstractBean> {

	public abstract boolean eval(ClientContext ctx, T bean);
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
}