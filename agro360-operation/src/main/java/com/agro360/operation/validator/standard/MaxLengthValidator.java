package com.agro360.operation.validator.standard;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.validator.common.Validator;

public class MaxLengthValidator extends Validator<AbstractBean> {

	public MaxLengthValidator() {
		super(null);
	}
	
	@Override
	public boolean validate(ClientContext ctx, AbstractBean bean, String fieldName) {
		var field = bean.getField(fieldName);
		var value = field.getValue();
		var maxLength = field.getMaxLength();
		
		var valide = value == null || value.toString().length() <= maxLength;
		
		if( !valide ) {
			var msgTpl = "La longueur du champ dépasse la capacité maximale %d autorisée!";
			field.addMessage(Message.error(String.format(msgTpl, maxLength)));
		}
		
		return valide;
	}

}