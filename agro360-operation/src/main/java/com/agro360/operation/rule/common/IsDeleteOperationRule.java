package com.agro360.operation.rule.common;

import com.agro360.vd.common.ClientOperationEnumVd;

/**
 * Checks if the underlined operation is the delete
 */
public class IsDeleteOperationRule extends AbstractClientOperationRule{

	public IsDeleteOperationRule() {
		super(ClientOperationEnumVd.INIT_DELETE_FORM);
	}
	
}