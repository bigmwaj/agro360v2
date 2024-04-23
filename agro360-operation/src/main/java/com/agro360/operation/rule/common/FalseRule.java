package com.agro360.operation.rule.common;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

public class FalseRule extends AbstractRule{
	
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return false;
	}
}