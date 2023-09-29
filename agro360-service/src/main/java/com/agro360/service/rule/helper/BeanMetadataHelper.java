package com.agro360.service.rule.helper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(value={"schemaLocation"}, ignoreUnknown = false)
@EqualsAndHashCode(callSuper = false)
@Data
public class BeanMetadataHelper {
	
	private String name;
	
	private String namespace;
	
	private boolean visible;

	private boolean required;
	
	private List<ConstraintHelper> visibleContraints;
	
	private boolean editable;
	
//	@JacksonXmlElementWrapper(useWrapping = true, localName = "editableContraint")
//	private List<ConstraintHelper> constraint;
	
	//@JacksonXmlProperty(namespace = "editableContraint", localName = "constraint")
	@JacksonXmlElementWrapper(useWrapping = true, localName = "editableContraint")
	private List<ConstraintHelper> constraint;
	
	@JacksonXmlElementWrapper(useWrapping = false, localName = "field", namespace = "field")
	private List<FieldMetadataHelper> field;

	@JacksonXmlElementWrapper(useWrapping = false, localName = "bean", namespace = "bean")
	private List<BeanMetadataHelper> bean;
}
