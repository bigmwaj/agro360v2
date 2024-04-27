package com.agro360.operation.action.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

public abstract class AbstractAction<T, B extends AbstractBean> {

	public abstract T process(ClientContext ctx, B bean);

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	public void performAction(ClientContext ctx, B bean, String fieldName, String attributeName) {
		var result = this.process(ctx, bean);
		var field = bean.getField(fieldName);
		field.setAttribute(attributeName, result);
	}
}