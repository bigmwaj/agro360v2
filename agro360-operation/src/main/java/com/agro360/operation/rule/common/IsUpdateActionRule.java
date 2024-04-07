package com.agro360.operation.rule.common;

import com.agro360.vd.common.EditActionEnumVd;

/**
 * Checks if the underlined operation is the update
 */
public class IsUpdateActionRule extends AbstractBeanEditActionRule{

	public IsUpdateActionRule() {
		super(EditActionEnumVd.UPDATE);
	}
	
}