package com.agro360.service.rule.lookup.common;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;

@Component
public class SelfBeanLookup extends AbstractBeanLookup {

	public AbstractBean lookup(AbstractBean root, AbstractBean bean) {
		return bean;
	}
}
