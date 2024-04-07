package com.agro360.operation.rule.common;

import com.agro360.vd.common.EditActionEnumVd;

/**
 * Checks if the underlined operation is the creation
 */
public class IsCreateActionRule extends AbstractBeanEditActionRule{

	public IsCreateActionRule() {
		super(EditActionEnumVd.CREATE);
	}
	
}