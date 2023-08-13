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

	private T value;

	@JsonIgnore
	private transient boolean required;

	@JsonIgnore
	private transient boolean editable = true;

	@JsonIgnore
	private transient Integer maxLength;

	@JsonIgnore
	private transient Double max;

	@JsonIgnore
	private transient Double min;

	private transient Map<T, String> valueOptions;

	@JsonIgnore
	private transient String tooltip;

	@JsonIgnore
	private transient String icon;

	@JsonIgnore
	private final List<Message> messages = new ArrayList<>();
	
	public void addMessage(Message message) {
		messages.add(message);
	}

	public boolean hasAnyError() {
		return Message.hasAnyError(messages);
	}

}
