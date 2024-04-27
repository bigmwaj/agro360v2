package com.agro360.operation.rule.common;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

public class TrueRule extends AbstractRule<AbstractBean>{
	
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return true;
	}
}