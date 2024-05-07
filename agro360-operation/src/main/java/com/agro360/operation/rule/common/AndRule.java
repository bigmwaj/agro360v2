package com.agro360.operation.rule.common;

import java.util.List;
import java.util.function.Predicate;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.exception.RuleException;

public class AndRule extends AbstractRule<AbstractBean>{
	
	private final List<AbstractRule<AbstractBean>> rules;
	
	public AndRule(List<AbstractRule<AbstractBean>> rules) {
		this.rules = rules;
	}
	
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		if( rules == null || rules.isEmpty() ) {
			throw new RuleException("Aucune règle à évaluer");
		}
		Predicate<AbstractRule<AbstractBean>> eval = e -> e.eval(ctx, bean);
		return rules.stream().allMatch(eval);
	}
}