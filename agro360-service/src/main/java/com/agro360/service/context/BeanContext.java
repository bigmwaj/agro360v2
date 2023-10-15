package com.agro360.service.context;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BeanContext {

	private String ruleName;
	
	private String operation;
}
