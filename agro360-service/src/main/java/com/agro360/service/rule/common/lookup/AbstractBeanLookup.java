package com.agro360.service.rule.common.lookup;

import com.agro360.service.bean.common.AbstractBean;

public abstract class AbstractBeanLookup {

	public abstract AbstractBean lookup(AbstractBean root, AbstractBean bean);
}
