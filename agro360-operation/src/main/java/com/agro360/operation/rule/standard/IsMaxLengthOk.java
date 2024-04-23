package com.agro360.operation.rule.standard;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.message.Message;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.operation.rule.exception.RuleException;

/**
 * Règle permettant de s'assurer que la longueur d'un texte ne dépasse pas une
 * valeur donnée.
 * 
 * @author bigmwaj
 */
@Component
public class IsMaxLengthOk extends AbstractRule {

	public static final String PARAM_KEY = "length";

	public static final String MESSAGE_KEY = "message";

	private static final String ERROR_MSG = "La longueur du champ doit inférieure à %d!";

	private static final String MSG1 = String.format("Un paramètre de clé %s doit être définit", PARAM_KEY);

	private static final String MSG2 = String.format("Le paramètre de clé %s doit être de type entier", PARAM_KEY);

	private static final String MSG3 = String.format("La valeur du paramètre %s doit être strictement supérieure",
			PARAM_KEY);

	protected Integer getParamValue(Map<String, Object> params) {
		if (!params.containsKey(PARAM_KEY)) {
			throw new RuleException(MSG1);
		}

		var _paramValue = params.get(PARAM_KEY);

		if (!(_paramValue instanceof Integer)) {
			throw new RuleException(MSG2);
		}

		var paramValue = (Integer) _paramValue;

		if (paramValue <= 0) {
			throw new RuleException(MSG3);
		}
		return paramValue;
	}

	public boolean apply(AbstractBean bean, String fieldName, Map<String, Object> params) {
		var length = getParamValue(params);

		params.remove(PARAM_KEY);
		FieldMetadata<?> field = bean.getField(fieldName);
		var value = field.getValue();

		if (value != null && value.toString().length() > length) {
			var msg = String.format(ERROR_MSG, length);
			if (params.containsKey(MESSAGE_KEY)) {
				msg = (String) params.get(MESSAGE_KEY);
			}
			field.addMessage(Message.error(msg));
		}
		return !field.hasAnyError();
	}
	
	@Override
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		return false;
	}
}