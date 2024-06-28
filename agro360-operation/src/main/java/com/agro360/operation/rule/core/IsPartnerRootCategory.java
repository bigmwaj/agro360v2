package com.agro360.operation.rule.core;

import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

public class IsPartnerRootCategory extends AbstractRule<PartnerCategoryBean> {
	
	@Override
	public boolean eval(ClientContext ctx, PartnerCategoryBean bean) {
		String categoryCode = null;
		categoryCode = bean.getCategoryCode().getValue();
		return "ROOT".equals(categoryCode);
	}
}