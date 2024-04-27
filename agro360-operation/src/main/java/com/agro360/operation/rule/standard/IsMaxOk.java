package com.agro360.operation.rule.standard;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.operation.rule.exception.RuleException;

@Component
public class IsMaxOk extends AbstractRule<AbstractBean> {
	
	private final static String ERROR_MSG = "La valeur doit être %s inférieure à %d!";

	public static final String PARAM_KEY = "max";

	public static final String PARAM_STRICT_KEY = "strict";
	
	public static final String REQUIRED_NUMBER = "Nombre requis";

	public static final String MESSAGE_KEY = "message";

	private static final String MSG1 = String.format("Un paramètre de clé %s doit être définit", PARAM_KEY);

	private static final String MSG2 = String.format("Le paramètre de clé %s doit être de type Numéric", PARAM_KEY);

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
		var field = bean.getField(fieldName);
		var _value = field.getValue();
		
		if( _value == null ) {
			return true;
		}
		
		if (!(_value instanceof Number)) {
			throw new RuleException(REQUIRED_NUMBER);
		}
		var value = (Number) _value;
		var max = getParamValue(params);

		var strict = false;
		if (params.containsKey(PARAM_STRICT_KEY)) {
			strict = (boolean) params.get(PARAM_STRICT_KEY);
		}

		if ((strict && value.doubleValue() >= max.doubleValue())
				|| (!strict && value.doubleValue() > max.doubleValue())) {
			var msg = String.format(ERROR_MSG, (strict ? "strictement" : ""), max);
			if (params.containsKey(MESSAGE_KEY)) {
				msg = (String) params.get(MESSAGE_KEY);
			}
			field.addMessage(Message.error(msg));
		}
		return !field.hasAnyError();
	}
	
	@Override
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return true;
	}
}