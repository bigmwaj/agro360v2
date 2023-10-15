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
	
	@JacksonXmlElementWrapper(useWrapping = true)
	private List<ConstraintHelper> visibleContraints;
	
	private boolean editable;
	
	@JacksonXmlElementWrapper(useWrapping = true)
	private List<ConstraintHelper> editableContraints;
	
	@JacksonXmlElementWrapper(useWrapping = false, localName = "field")
	private List<FieldMetadataHelper> field;

	@JacksonXmlElementWrapper(useWrapping = false, localName = "bean")
	private List<BeanMetadataHelper> bean;
	
	@JacksonXmlElementWrapper(useWrapping = false, namespace = "bean-list")
	private List<BeanMetadataHelper> beanList;
	
	@JacksonXmlElementWrapper(useWrapping = false, namespace = "validation")
	private List<ValidationHelper> validation;

	@Override
	public String toString() {
		return "BeanMetadataHelper [\n\tname=" + name + ", \n\tnamespace=" + namespace + ", \n\tvisible=" + visible +
	 ", \n\tvisibleContraints=" + visibleContraints + ", "
						+ "\n\teditable=" + editable
				+ ", \n\teditableContraints=" + editableContraints + ", \n\tfield=" + field + ", \n\tbean=" + bean + "]";
	}
	
	
}
