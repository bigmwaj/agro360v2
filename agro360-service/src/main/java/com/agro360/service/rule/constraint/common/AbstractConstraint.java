package com.agro360.service.rule.constraint.common;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;

public abstract class AbstractConstraint {

	public boolean apply(BeanContext ctx, AbstractBean bean) {
		return true;
	}
}