package com.agro360.service.rule.validation.common;

import com.agro360.service.bean.common.AbstractBean;

public abstract class AbstractBeanValidator {

	public abstract AbstractBean validate(AbstractBean root, AbstractBean bean);
}
