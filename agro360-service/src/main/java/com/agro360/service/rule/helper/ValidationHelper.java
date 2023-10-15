package com.agro360.service.rule.helper;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ValidationHelper{

	@JacksonXmlElementWrapper(useWrapping = false, localName = "validator")
	private List<RuleHelper> validator;
	
	@JacksonXmlElementWrapper(useWrapping = false, localName = "contraint")
	private List<ConstraintHelper> constraint;
}
