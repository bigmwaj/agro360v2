package com.agro360.operation.rule.common;

import com.agro360.vd.common.EditActionEnumVd;

/**
 * Checks if the underlined operation is the delete
 */
public class IsDeleteActionRule extends AbstractBeanEditActionRule{

	public IsDeleteActionRule() {
		super(EditActionEnumVd.DELETE);
	}
	
}