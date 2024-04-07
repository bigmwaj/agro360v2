package com.agro360.operation.rule.helper;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ValidationHelper {
	
	@JacksonXmlElementWrapper(useWrapping = false, localName = "contraint")
	private List<ConstraintHelper> constraint;

	@JacksonXmlElementWrapper(useWrapping = true)
	private List<RuleHelper> rule;
}
