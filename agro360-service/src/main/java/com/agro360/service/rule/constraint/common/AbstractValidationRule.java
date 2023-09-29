package com.agro360.service.rule.constraint.common;

public abstract class AbstractValidationRule extends AbstractRule {

	@Override
	public boolean skipOnError() {
		return true;
	}

}