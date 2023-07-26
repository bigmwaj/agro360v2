package com.agro360.service.rule.common;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.service.utils.Message;

@Component
public class In extends AbstractValidationRule {

	private final static String ERROR_MSG = "Valeur Invalide! Elle doit être dans la liste [%s]";

	public static final String PARAM_KEY = "VALUES_KEY";

	private static final String MSG1 = String.format("Un paramètre de clé %s doit être définit",
			PARAM_KEY);

	private static final String MSG2 = String
			.format("Le paramètre de clé %s doit être de type Object[]", PARAM_KEY);

	private static final String MSG3 = String
			.format("Au moins une valeur pour le paramètre de clé %s doit être définit", PARAM_KEY);;

	private Object[] getParamValue(Map<String, Object> params) {

		if (!params.containsKey(PARAM_KEY)) {
			throw new RuleException(MSG1);
		}

		var _paramValue = params.get(PARAM_KEY);

		if (!(_paramValue instanceof Object[])) {
			throw new RuleException(MSG2);
		}

		var paramValue = (Object[]) _paramValue;

		if (paramValue.length == 0) {
			throw new RuleException(MSG3);
		}
		return paramValue;
	}

	/**
	 * Vérifie si la valeur se trouve dans la liste des valeurs en paramètre. Les
	 * valeurs sont sous form (clé, valeur) Remarques: null est équivalent à ''
	 * qu'il s'agisse de la valeur ou d'une clé dans la liste des valeurs
	 * 
	 * @param array params (Value, Label) pair. La recherche est basée sur la clé
	 */
	public boolean apply(AbstractBean bean, String fieldName, Map<String, Object> params) {
		FieldMetadata<?> field = bean.getField(fieldName);
		var value = field.getValue();
		if( value == null ) {
			return false;
		}
		var values = getParamValue(params);
		if (Arrays.stream(values).noneMatch(value::equals)) {
			var msg = String.format(ERROR_MSG,
					Arrays.stream(values).reduce("", (a, b) -> "".equals(a) ? b : a + ":" + b));
			field.addMessage(Message.error(msg));
		}
		return !field.hasAnyError();
	}
}