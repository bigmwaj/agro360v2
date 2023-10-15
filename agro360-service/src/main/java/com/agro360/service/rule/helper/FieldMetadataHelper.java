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

	@JacksonXmlElementWrapper(localName = "visibleContraints")
	private List<ConstraintHelper> visibleContraints;

	@JacksonXmlElementWrapper(localName = "editableContraints")
	private List<ConstraintHelper> editableContraints;

	@JacksonXmlElementWrapper(localName = "requiredContraints")
	private List<ConstraintHelper> requiredContraints;
	
	@JacksonXmlElementWrapper(useWrapping = false, namespace = "validation")
	private List<ValidationHelper> validation;

	@Override
	public String toString() {
		return "\n\t\tFieldMetadataHelper [name=" + name + ", visibleContraints=" + visibleContraints
				+ ", editableContraints=" + editableContraints + ", requiredContraints=" + requiredContraints + "]";
	}

}
