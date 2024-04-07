package com.agro360.operation.rule.common;

import org.springframework.stereotype.Component;

import com.agro360.vd.common.ClientOperationEnumVd;

/**
 * Check if the underlined is an editable operation
 */
@Component("common/IsEditableBean")
public class IsEditableOperationRule extends AbstractClientOperationRule{

	public IsEditableOperationRule() {
		super(ClientOperationEnumVd.INIT_CREATE_FORM, 
				ClientOperationEnumVd.INIT_UPDATE_FORM,
				ClientOperationEnumVd.INIT_CHANGE_STATUS_FORM);
	}
}