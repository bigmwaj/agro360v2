package com.agro360.service.rule.constraint.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;

/**
 * Check if the root bean is under creation
 */
public class IsValidationAction extends AbstractConstraint{

	@Override
	public boolean apply(BeanContext ctx, AbstractBean bean) {
		return "validation-form".equals(ctx.getOperation());
	}
	
}