package com.agro360.service.rule.common.lookup;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;

@Component
public class RootBeanLookup extends AbstractBeanLookup {

	public AbstractBean lookup(AbstractBean root, AbstractBean bean) {
		return root;
	}
}
