package com.agro360.service.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.agro360.service.message.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FieldMetadata<T> {

	private final String __TYPE__ = "FIELD_METADATA";

	public FieldMetadata() {
	}

	public FieldMetadata(String label) {
		this.label = label;
	}

	public FieldMetadata(String label, Map<Object, String> valueOptions) {
		this.label = label;
		this.valueOptions = valueOptions;
	}

	private T value;

	private String valueI18n;

	private String label;

	@JsonIgnore
	private boolean required;

	private boolean valueChanged;

	@JsonIgnore
	private boolean editable = true;

	@JsonIgnore
	private Integer maxLength;

	@JsonIgnore
	private Double max;

	@JsonIgnore
	private Double min;

	private Map<Object, String> valueOptions;

	@JsonIgnore
	private String tooltip;

	@JsonIgnore
	private String icon;

	private final List<Message> messages = new ArrayList<>();

	public void addMessage(Message message) {
		messages.add(message);
	}

	public boolean hasAnyError() {
		return Message.hasAnyError(messages);
	}

}
