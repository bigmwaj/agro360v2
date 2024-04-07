package com.agro360.bo.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.agro360.bo.message.Message;
import com.agro360.bo.utils.TypeScriptInfos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FieldMetadata<T> {

	private final String __TYPE__ = "FIELD_METADATA";

	public FieldMetadata() {}

	public FieldMetadata(String label) {
		this.label = label;
	}

	public FieldMetadata(String label, Map<Object, String> valueOptions) {
		this.label = label;
		this.valueOptions = valueOptions;
	}

	@TypeScriptInfos(type = "T")
	private T value;

	private String valueI18n;

	private String label;

	private boolean required;

	private boolean visible = true;

	private boolean valueChanged;

	private boolean editable = true;

	private Integer maxLength;

	private Double max;

	private Double min;

	private Map<Object, String> valueOptions;

	private String tooltip;

	private String icon;

	private final List<Message> messages = new ArrayList<>();

	public void addMessage(Message message) {
		messages.add(message);
	}

	public boolean hasAnyError() {
		return Message.hasAnyError(messages);
	}

}
