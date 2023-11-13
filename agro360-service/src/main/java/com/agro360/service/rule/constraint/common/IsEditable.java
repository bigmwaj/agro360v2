package com.agro360.service.rule.constraint.common;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;

/**
 * Check if the root bean is editable
 */
@Component("common/IsEditableBean")
public class IsEditable extends AbstractConstraint{

	public boolean apply(BeanContext ctx, AbstractBean bean) {
		return true;
	}
}