package com.agro360.operation.rule.common;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.BeanRuleContext;

public abstract class AbstractRule {

	public abstract boolean apply(BeanRuleContext ctx, AbstractBean bean);
}