package com.agro360.service.rule.constraint.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;

public class IsStatusChangeAction extends AbstractConstraint{

	@Override
	public boolean apply(BeanContext ctx, AbstractBean bean) {
		return "init-change-status-form".equals(ctx.getOperation());
	}
	
}