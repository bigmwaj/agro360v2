package com.agro360.operation.rule.module.common;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class IsBeanChangeStatusOperation extends AbstractRule {
	
	@Override
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return ClientOperationEnumVd.CHANGE_STATUS.equals(bean.getRootBean().getAction());
	}
}