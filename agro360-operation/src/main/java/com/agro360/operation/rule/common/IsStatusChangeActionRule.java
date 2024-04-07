package com.agro360.operation.rule.common;

import com.agro360.vd.common.EditActionEnumVd;

/**
 * Checks if the underlined operation is the change status
 */
public class IsStatusChangeActionRule extends AbstractBeanEditActionRule{

	public IsStatusChangeActionRule() {
		super(EditActionEnumVd.CHANGE_STATUS);
	}
	
}