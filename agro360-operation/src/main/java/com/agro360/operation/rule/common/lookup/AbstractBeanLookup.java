package com.agro360.operation.rule.common.lookup;

import com.agro360.bo.bean.common.AbstractBean;

public abstract class AbstractBeanLookup {

	public abstract AbstractBean lookup(AbstractBean root, AbstractBean bean);
}
