package com.agro360.service.rule.helper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class RuleHelper{

	@JacksonXmlElementWrapper(localName = "class")
	private String ruleClass;
	
	@JacksonXmlElementWrapper(localName = "lookupClass")
	private String lookupClass;
}
