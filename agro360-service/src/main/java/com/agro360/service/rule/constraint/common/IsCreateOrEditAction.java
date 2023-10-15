package com.agro360.service.rule.constraint.common;

import java.util.Objects;
import java.util.stream.Stream;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanContext;

/**
 * Check if the root bean is under creation or modification
 */
public class IsCreateOrEditAction extends AbstractConstraint{

	@Override
	public boolean apply(BeanContext ctx, AbstractBean bean) {
		
		return Objects.nonNull(ctx.getOperation()) 
				&& Stream.of("init-create-form", "init-edit-form").anyMatch(ctx.getOperation()::equals);
	}
	
}