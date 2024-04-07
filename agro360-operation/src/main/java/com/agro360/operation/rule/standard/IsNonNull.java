package com.agro360.operation.rule.standard;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.message.Message;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.operation.context.BeanRuleContext;
import com.agro360.operation.rule.common.AbstractRule;

@Component
public class IsNonNull extends AbstractRule {

	public static final String MESSAGE_KEY = "message";

	public static final String MSG = "La valeur est requise!";

	public boolean apply(AbstractBean bean, String fieldName, Map<String, Object> params) {
		FieldMetadata<?> field = bean.getField(fieldName);

		if (field.getValue() == null) {
			var msg = MSG;
			if (params.containsKey(MESSAGE_KEY)) {
				msg = (String) params.get(MESSAGE_KEY);
			}
			field.addMessage(Message.error(msg));
		}
		return !field.hasAnyError();
	}
	
	@Override
	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		return false;
	}
}