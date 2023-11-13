package com.agro360.service.rule.helper;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ConstraintHelper {

	@JacksonXmlElementWrapper(useWrapping = false)
	private List<RuleHelper> rule;
}
