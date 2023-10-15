package com.agro360.service.rule.constraint.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;


public class InitFormConstraint extends AbstractConstraint{
	
	private String operation;
	
	public InitFormConstraint(String operation) {
		this.operation = operation;
	}

	@Override
	public boolean apply(BeanContext ctx, AbstractBean bean) {
		return operation.equals(ctx.getOperation());
	}
	
}