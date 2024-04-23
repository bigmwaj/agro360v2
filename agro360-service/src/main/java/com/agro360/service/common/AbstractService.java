package com.agro360.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractService {
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
}
