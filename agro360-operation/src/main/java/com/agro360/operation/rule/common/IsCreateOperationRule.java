package com.agro360.operation.rule.common;

import com.agro360.vd.common.ClientOperationEnumVd;

/**
 * Checks if the underlined operation is the creation
 */
public class IsCreateOperationRule extends AbstractClientOperationRule{

	public IsCreateOperationRule() {
		super(ClientOperationEnumVd.INIT_CREATE_FORM);
	}
	
}