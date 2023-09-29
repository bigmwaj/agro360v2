package com.agro360.service.rule.constraint.common;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;

@Component
public class IsEditableBean {

	public boolean apply(AbstractBean bean) {
		return true;
	}
}