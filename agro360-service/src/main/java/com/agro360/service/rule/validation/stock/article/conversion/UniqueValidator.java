package com.agro360.service.rule.validation.stock.article.conversion;

import com.agro360.service.bean.common.AbstractBean;

public abstract class UniqueValidator {

	public abstract AbstractBean validate(AbstractBean root, AbstractBean bean);
}
