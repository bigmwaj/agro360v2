package com.agro360.service.rule.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.service.utils.Message;

/**
 * Règle constituée d'un ensemble de règles. 
 * La règle est valide si au moins une
 * des règles de l'ensemble est valide. Exemple d'utilisation:
 * self::VALIDATION_RULE=>[All::class=>['rules'=>[RuleA::class=>[],
 * RuleB::class=>[]]]]
 */
@Component
public class Any extends AbstractCollectionBasedRule {

	private final static String MSG = "Valeur Invalide!";

	public static final String MESSAGE_KEY = "message";

	public boolean apply(AbstractBean bean, String fieldName, Map<String, Object> params) {
		var rules = getParamValue(params);

		for (String ruleClass : rules.keySet()) {
			var prms = new HashMap<>(params);
			prms.putAll(rules.get(ruleClass));
			var ruleObj = getRuleByClassName(ruleClass);

			var passed = ruleObj.apply(bean, fieldName, prms);
			if (passed) {
				return true;
			} else {
				var message = MSG;
				if (prms.containsKey(MESSAGE_KEY)) {
					FieldMetadata<?> field = bean.getField(fieldName);
					field.addMessage(Message.error(message));
				}
			}
		}
		return false;
	}
}