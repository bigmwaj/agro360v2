package com.agro360.service.rule.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.message.Message;
import com.agro360.service.metadata.FieldMetadata;

/**
 * Agregation de règles. Les règles sont évaluées de la gauche vers la droite et
 * si l'une d'elle est invalide, l'ensemble est invalide Exemple d'utilisation:
 * self::VALIDATION_RULE=>[All::class=>['rules'=>[RuleA::class=>[],
 * RuleB::class=>[]]]]
 */
@Component
public class All extends AbstractCollectionBasedRule {
	
	protected static final String MSG = "Valeur Invalide!";

	public static final String MESSAGE_KEY = "message";

	public boolean apply(AbstractBean bean, String fieldName, Map<String, Object> params) {
		var rules = getParamValue(params);

		for (String ruleClass : rules.keySet()) {
			var prm = new HashMap<>(params);
			prm.putAll(rules.get(ruleClass));
			var ruleObj = getRuleByClassName(ruleClass);
			var passed = ruleObj.apply(bean, fieldName, prm);
			if (!passed) {
				var message = MSG;
				if (prm.containsKey(MESSAGE_KEY)) {
					message = (String) prm.get(MESSAGE_KEY);
				}
				FieldMetadata<?> field = bean.getField(fieldName);
				field.addMessage(Message.error(message));
				return false;
			}
		}
		return true;
	}
}