package com.agro360.service.rule.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanRuleContext;

/**
 * Checks if the underlined operation is the creation
 */
public class IsCreateOperationRule extends AbstractRule{

	@Override
	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		return "init-create-form".equals(ctx.getOperation());
	}
	
}