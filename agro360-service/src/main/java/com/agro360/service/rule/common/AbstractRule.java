package com.agro360.service.rule.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanRuleContext;

public abstract class AbstractRule {

	public abstract boolean apply(BeanRuleContext ctx, AbstractBean bean);
}