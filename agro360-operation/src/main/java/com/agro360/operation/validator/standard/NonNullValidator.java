package com.agro360.operation.validator.standard;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.validator.common.Validator;

@Component
public class NonNullValidator extends Validator<AbstractBean> {

	public NonNullValidator() {
		super(null);
	}
	
	@Override
	public boolean validate(ClientContext ctx, AbstractBean bean, String fieldName) {
		var field = bean.getField(fieldName);
		var value = field.getValue();
		var required = field.isRequired();
		
		var valide = !required || value != null || (value instanceof String && !value.toString().isBlank());
		
		if( !valide ) {
			var msgTpl = "La valeur de ce champ est requise!";
			field.addMessage(Message.error(msgTpl));
		}
		
		return valide;
	}
}