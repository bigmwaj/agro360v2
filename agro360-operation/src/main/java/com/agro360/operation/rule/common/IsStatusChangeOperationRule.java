package com.agro360.operation.rule.common;

import com.agro360.vd.common.ClientOperationEnumVd;

/**
 * Checks if the underlined operation is the change status
 */
public class IsStatusChangeOperationRule extends AbstractClientOperationRule{

	public IsStatusChangeOperationRule() {
		super(ClientOperationEnumVd.INIT_CHANGE_STATUS_FORM);
	}
	
}