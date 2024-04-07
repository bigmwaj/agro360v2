package com.agro360.operation.rule.common;

import com.agro360.vd.common.ClientOperationEnumVd;

/**
 * Checks if the underlined operation is the validation
 */
public class IsValidationOperationRule extends AbstractClientOperationRule{

	public IsValidationOperationRule() {
		super(ClientOperationEnumVd.VALIDATE_FORM);
	}
}