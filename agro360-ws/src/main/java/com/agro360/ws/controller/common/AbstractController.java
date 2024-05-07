package com.agro360.ws.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.operation.context.ClientContext;

public class AbstractController {

	protected final static String RECORDS_MODEL_KEY = "records";
	
	protected final static String RECORDS_TOTAL_KEY = "total";
	
	protected final static String RECORD_MODEL_KEY = "record";

	protected final static String MESSAGES_MODEL_KEY = "messages";
	
	protected final static String SEARCH_FORM_RN = "search-form";
	
	protected final static String CREATE_FORM_RN = "create-form";
	
	protected final static String CREATE_FORM_RN(String postfix) {
		return String.format("%s/%s", postfix, CREATE_FORM_RN);
	}
	
	protected final static String EDIT_FORM_RN = "edit-form";
	
	protected final static String DELETE_FORM_RN = "delete-form";
	
	protected final static String DELETE_FORM_RN(String postfix) {
		return String.format("%s/%s", postfix, DELETE_FORM_RN);
	}
	
	protected final static String CHANGE_STATUS_FORM_RN = "change-status-form";
	
	protected ClientContext getClientContext() {
		return new ClientContext();
	}

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
}
