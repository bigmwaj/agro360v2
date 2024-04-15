package com.agro360.form.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractForm {
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
}
