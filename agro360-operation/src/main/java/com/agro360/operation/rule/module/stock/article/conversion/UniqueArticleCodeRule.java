package com.agro360.operation.rule.module.stock.article.conversion;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

public class UniqueArticleCodeRule extends AbstractRule{

	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return true;
	}
}
