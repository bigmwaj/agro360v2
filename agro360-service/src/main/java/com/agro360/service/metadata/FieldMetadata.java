package com.agro360.service.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.agro360.service.utils.Message;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FieldMetadata<T> {

	private T value;

	private boolean required;

	private boolean editable;

	private Integer maxLength;

	private Double max;

	private Double min;

	private Map<String, Object> valueOptions;

	private String tooltip;

	private String icon;

	private final List<Message> messages = new ArrayList<>();

	@Override
	public String toString() {
		return "FieldMetadata [value=" + value + "]";
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	public boolean hasAnyError() {
		return messages.stream().map(Message::getType).anyMatch(Message.TYPE.ERROR::equals);
	}

}
