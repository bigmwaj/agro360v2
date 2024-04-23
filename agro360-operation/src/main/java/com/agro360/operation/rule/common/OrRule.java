package com.agro360.operation.rule.common;

import java.util.List;
import java.util.function.Predicate;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.exception.RuleException;

public class OrRule extends AbstractRule{
	
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		if( rules == null || rules.isEmpty() ) {
			throw new RuleException("Aucune règle à évaluer");
		}
		Predicate<AbstractRule> eval = e -> e.eval(ctx, bean);
		return rules.stream().anyMatch(eval);
	}
	
	private List<AbstractRule> rules;
	
	public OrRule(List<AbstractRule> rules) {
		this.rules = rules;
	}
}