package com.agro360.operation.context;

import com.agro360.vd.common.ClientOperationEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BeanRuleContext {
	
	private final ClientContext userContext;

	private final ClientOperationEnumVd operation;
	
	public BeanRuleContext(ClientContext userContext, ClientOperationEnumVd operation) {
		super();
		this.operation = operation;
		this.userContext = userContext;
	}
	
	public BeanRuleContext(ClientOperationEnumVd operation) {
		super();
		this.operation = operation;
		this.userContext = new ClientContext();
	}
}
