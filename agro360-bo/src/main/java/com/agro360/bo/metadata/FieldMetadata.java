package com.agro360.bo.metadata;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.bo.message.Message;
import com.agro360.bo.utils.TypeScriptInfos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FieldMetadata<T> {

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

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

	private boolean editable = false;

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

	private String getSetterName(String attrName) {
		return "set" + ((attrName.charAt(0) + "").toUpperCase()) + attrName.substring(1);
	}

	private Method getAttributeSetterMethod(String attrName) {
		var methodName = getSetterName(attrName);
		Predicate<Method> isAttrSetter = e -> e.getName().equals(methodName);
		var setters = Arrays.stream(getClass().getMethods()).filter(isAttrSetter)
				.collect(Collectors.toList());

		if( setters.size() != 1 ) {
			var msgTpl = "%d setter(s) de l'attribut %s défini(s) dans la classe %s alors qu'exactement un seul est attendu";
			var className = getClass().getName();
			throw new RuntimeException(String.format(msgTpl, setters.size(), attrName, className));
		}
		var attrSetter = setters.get(0);

		var paramCount = attrSetter.getParameterCount();
		if( paramCount != 1 ) {
			var msgTpl = "%d paramètre(s) du setter de l'attribut %s défini(s) dans la classe %s alors qu'exactement un seul est attendu";
			var className = getClass().getName();
			throw new RuntimeException(String.format(msgTpl, paramCount, attrName, className));
		}
		return attrSetter;
	}

	private Object getCastedValue(String attrName, Object value) {
		switch (attrName) {
		case "required":
		case "visible":
		case "editable":
			return Boolean.valueOf(value.toString());

		case "valueI18n":
		case "label":
		case "tooltip":
		case "icon":
			return value.toString();

		case "valueOptions":
			return value;

		case "maxLength":
			return Integer.valueOf(value.toString());

		case "max":
		case "min":
			return Double.valueOf(value.toString());

		default:
			break;
		}
		return value;
	}

	public void setAttribute(String attrName, Object value) {
		try {
			var attrSetter = getAttributeSetterMethod(attrName);
			attrSetter.invoke(this, getCastedValue(attrName, value));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
