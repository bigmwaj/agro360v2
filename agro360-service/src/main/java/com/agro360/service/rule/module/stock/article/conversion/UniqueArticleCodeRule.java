package com.agro360.service.rule.module.stock.article.conversion;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanRuleContext;
import com.agro360.service.rule.common.AbstractRule;

public class UniqueArticleCodeRule extends AbstractRule{

	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		return true;
	}
}
