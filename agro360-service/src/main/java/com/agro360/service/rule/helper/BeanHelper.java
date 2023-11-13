package com.agro360.service.rule.helper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(value = { "schemaLocation" }, ignoreUnknown = false)
@EqualsAndHashCode(callSuper = false)
@Data
public class BeanHelper {

	private String name;

	private String namespace;

	private boolean visible = true;

	@JacksonXmlElementWrapper(useWrapping = true)
	private List<ConstraintHelper> visibleContraints;

	private boolean editable = true;

	@JacksonXmlElementWrapper(useWrapping = true)
	private List<ConstraintHelper> editableContraints;

	@JacksonXmlElementWrapper(useWrapping = false)
	private List<FieldMetadataHelper> field;

	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BeanHelper> bean;

	@JacksonXmlElementWrapper(useWrapping = false)
	private List<BeanHelper> beanList;

	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ValidationHelper> validation;

}
