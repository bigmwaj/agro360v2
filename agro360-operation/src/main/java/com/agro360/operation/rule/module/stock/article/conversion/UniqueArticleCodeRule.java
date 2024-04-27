package com.agro360.operation.rule.module.stock.article.conversion;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

public class UniqueArticleCodeRule extends AbstractRule<ArticleBean>{

	public boolean eval(ClientContext ctx, ArticleBean bean) {
		return true;
	}
}
