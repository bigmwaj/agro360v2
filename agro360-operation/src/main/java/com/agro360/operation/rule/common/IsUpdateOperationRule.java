package com.agro360.operation.rule.common;

import com.agro360.vd.common.ClientOperationEnumVd;

/**
 * Checks if the underlined operation is the update
 */
public class IsUpdateOperationRule extends AbstractClientOperationRule{

	public IsUpdateOperationRule() {
		super(ClientOperationEnumVd.INIT_UPDATE_FORM);
	}
	
}