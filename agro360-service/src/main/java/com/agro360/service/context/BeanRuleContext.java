package com.agro360.service.context;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BeanRuleContext {
	
	private final UserContext userContext;

	private final String operation;
	
	public BeanRuleContext(UserContext userContext, String operation) {
		super();
		this.operation = operation;
		this.userContext = userContext;
	}
	
	public BeanRuleContext(String operation) {
		super();
		this.operation = operation;
		this.userContext = new UserContext();
	}
}
