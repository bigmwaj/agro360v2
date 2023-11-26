package com.agro360.service.rule.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanRuleContext;

/**
 * Checks if the underlined operation is the change status
 */
public class IsStatusChangeOperationRule extends AbstractRule{

	@Override
	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		return "init-change-status-form".equals(ctx.getOperation());
	}
	
}