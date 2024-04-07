package com.agro360.operation.rule.common;

import java.util.Arrays;
import java.util.Objects;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.BeanRuleContext;
import com.agro360.vd.common.EditActionEnumVd;

public abstract class AbstractBeanEditActionRule extends AbstractRule{
	
	private final EditActionEnumVd[] actions;

	public AbstractBeanEditActionRule(EditActionEnumVd ... actions) {
		this.actions = actions;
	}
	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		
		return Objects.nonNull(bean.getAction()) 
				&& Arrays.stream(actions).anyMatch(bean.getAction()::equals);
	}
}