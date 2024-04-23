package com.agro360.operation.rule.module.common;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class IsBeanUpdateOperation extends AbstractRule {
	
	@Override
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return ClientOperationEnumVd.UPDATE.equals(bean.getRootBean().getAction());
	}
}