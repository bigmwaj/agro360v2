package com.agro360.operation.metadata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.rule.common.AbstractRule;

import lombok.Data;

@Data
public class ConstraintConfig {

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	private List<AbstractRule<AbstractBean>> rules;
	
	private List<AbstractAction<?, AbstractBean>> actions;
}
