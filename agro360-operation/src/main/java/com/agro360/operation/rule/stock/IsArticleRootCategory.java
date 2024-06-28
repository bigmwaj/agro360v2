package com.agro360.operation.rule.stock;

import com.agro360.bo.bean.stock.ArticleCategoryBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

public class IsArticleRootCategory extends AbstractRule<ArticleCategoryBean> {
	
	@Override
	public boolean eval(ClientContext ctx, ArticleCategoryBean bean) {
		String categoryCode = null;
		categoryCode = bean.getCategoryCode().getValue();
		return "ROOT".equals(categoryCode);
	}
}