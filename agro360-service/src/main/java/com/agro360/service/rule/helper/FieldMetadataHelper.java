package com.agro360.service.rule.helper;

import java.util.List;

import com.agro360.service.metadata.FieldMetadata;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class FieldMetadataHelper extends FieldMetadata<Object> {

	private String name;

	@JacksonXmlElementWrapper()
	private List<ConstraintHelper> visibleContraints;

	@JacksonXmlElementWrapper()
	private List<ConstraintHelper> editableContraints;

	@JacksonXmlElementWrapper()
	private List<ConstraintHelper> requiredContraints;

	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ValidationHelper> validation;

}
