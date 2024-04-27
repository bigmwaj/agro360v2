package com.agro360.operation.rule.common;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

public class IsOwnerEvalTrueRule extends AbstractRule<AbstractBean>{
	
	private final AbstractRule<AbstractBean> rule;
	
	public IsOwnerEvalTrueRule(AbstractRule<AbstractBean> rule) {
		this.rule = rule;
	}
	
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return rule.eval(ctx, bean.getOwnerBean());
	}
}