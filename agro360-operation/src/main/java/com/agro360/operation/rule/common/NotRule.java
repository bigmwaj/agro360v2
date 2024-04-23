package com.agro360.operation.rule.common;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

public class NotRule extends AbstractRule{
	
	private final AbstractRule rule;
	
	public NotRule(AbstractRule rule) {
		this.rule = rule;
	}

	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return !this.rule.eval(ctx, bean);
	}
}