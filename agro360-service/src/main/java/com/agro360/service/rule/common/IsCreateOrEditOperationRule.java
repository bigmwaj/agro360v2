package com.agro360.service.rule.common;

import java.util.Objects;
import java.util.stream.Stream;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanRuleContext;

/**
 * Checks if the underlined operation is the creation or modification
 */
public class IsCreateOrEditOperationRule extends AbstractRule{

	@Override
	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		
		return Objects.nonNull(ctx.getOperation()) 
				&& Stream.of("init-create-form", "init-edit-form")
				.anyMatch(ctx.getOperation()::equals);
	}
	
}