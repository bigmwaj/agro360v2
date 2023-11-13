package com.agro360.service.rule.validation.standard;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.message.Message;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.service.rule.constraint.common.AbstractValidationRule;
import com.agro360.service.rule.constraint.common.RuleException;

@Component
public class IsMinOk extends AbstractValidationRule {
	
	public static final String PARAM_KEY = "min";

	public static final String PARAM_STRICT_KEY = "strict";
	
	public static final String MESSAGE_KEY = "message";
	
	private final static String ERROR_MSG = "La valeur doit être %s supérieure à %d!";

	private static final String MSG1 = String.format("Un paramètre de clé %s doit être définit", PARAM_KEY);

	private static final String MSG2 = String.format("Le paramètre de clé %s doit être de type Integer", PARAM_KEY);

	protected Number getParamValue(Map<String, Object> params) {

		if (!params.containsKey(PARAM_KEY)) {
			throw new RuleException(MSG1);
		}

		var _paramValue = params.get(PARAM_KEY);

		if (!(_paramValue instanceof Number)) {
			throw new RuleException(MSG2);
		}

		return (Number) _paramValue;
	}

	public boolean apply(AbstractBean bean, String fieldName, Map<String, Object> params) {
		FieldMetadata<?> field = bean.getField(fieldName);
		var _value = field.getValue();
		if( _value == null ) {
			return true;
		}
		
		if (!(_value instanceof Number)) {
			throw new RuleException(REQUIRED_NUMBER);
		}
		var value = (Number) _value;
		var min = getParamValue(params);

		var strict = false;

		if (params.containsKey(PARAM_STRICT_KEY)) {
			strict = (boolean) params.get(PARAM_STRICT_KEY);
		}

		if ((strict && value.doubleValue() <= min.doubleValue())
				|| (!strict && value.doubleValue() < min.doubleValue())) {
			var msg = String.format(ERROR_MSG, (strict ? "strictement" : ""), min);
			if (params.containsKey(MESSAGE_KEY)) {
				msg = (String) params.get(MESSAGE_KEY);
			}
			field.addMessage(Message.error(msg));
		}
		return !field.hasAnyError();
	}
}