package com.agro360.operation.rule.core;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

public class IsRootCategory extends AbstractRule<CategoryBean> {
	
	@Override
	public boolean eval(ClientContext ctx, CategoryBean bean) {
		String categoryCode = null;
		categoryCode = bean.getCategoryCode().getValue();
		return "ROOT".equals(categoryCode);
	}
}