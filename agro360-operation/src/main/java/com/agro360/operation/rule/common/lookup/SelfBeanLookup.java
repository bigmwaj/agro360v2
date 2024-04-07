package com.agro360.operation.rule.common.lookup;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;

@Component
public class SelfBeanLookup extends AbstractBeanLookup {

	public AbstractBean lookup(AbstractBean root, AbstractBean bean) {
		return bean;
	}
}
