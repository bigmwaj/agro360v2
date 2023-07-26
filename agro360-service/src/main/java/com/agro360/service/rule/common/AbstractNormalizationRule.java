package com.agro360.service.rule.common;

public abstract class AbstractNormalizationRule extends AbstractRule {
	
	protected static final String SELECT_VALUE = "-- Sélectionnez une valeur --";

	@Override
	public boolean skipOnError() {
		return false;
	}
}