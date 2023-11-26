package com.agro360.service.rule.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanRuleContext;

/**
 * Checks if the underlined operation is the validation
 */
public class IsValidationOperationRule extends AbstractRule{

	@Override
	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		return "validation-form".equals(ctx.getOperation());
	}
	
}