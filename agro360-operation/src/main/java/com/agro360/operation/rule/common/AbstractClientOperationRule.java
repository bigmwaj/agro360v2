package com.agro360.operation.rule.common;

import java.util.Arrays;
import java.util.Objects;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.BeanRuleContext;
import com.agro360.vd.common.ClientOperationEnumVd;

public abstract class AbstractClientOperationRule extends AbstractRule{
	
	private final ClientOperationEnumVd[] operations;

	public AbstractClientOperationRule(ClientOperationEnumVd ... operations) {
		this.operations = operations;
	}
	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		
		return Objects.nonNull(ctx.getOperation()) 
				&& Arrays.stream(operations).anyMatch(ctx.getOperation()::equals);
	}
}