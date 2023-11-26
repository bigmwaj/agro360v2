package com.agro360.service.rule.common;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.context.BeanRuleContext;

/**
 * Check if the underlined is an editable operation
 */
@Component("common/IsEditableBean")
public class IsEditableOperationRule extends AbstractRule{

	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		return Objects.nonNull(ctx.getOperation()) 
				&& Stream.of("init-create-form", "init-edit-form", "init-change-status-form")
				.anyMatch(ctx.getOperation()::equals);
	}
}