package com.agro360.operation.rule.common;

import com.agro360.vd.common.ClientOperationEnumVd;

/**
 * Checks if the underlined operation is the creation or modification
 */
public class IsCreateOrUpdateOperationRule extends AbstractClientOperationRule{

	public IsCreateOrUpdateOperationRule() {
		super(ClientOperationEnumVd.INIT_CREATE_FORM, ClientOperationEnumVd.INIT_UPDATE_FORM);
	}
}