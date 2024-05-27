package com.agro360.operation.validator.standard;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.validator.common.Validator;

@Component
public class MinValidator extends Validator<AbstractBean> {
	
	public MinValidator() {
		super(null);
	}
	
	@Override
	public boolean validate(ClientContext ctx, AbstractBean bean, String fieldName) {
		var field = bean.getField(fieldName);
		var value = field.getValue();
		var min = field.getMin();
		
		var valide = true;
		
		if( min != null && value instanceof Number ) {
			var num = (Number)value;
			valide = min.compareTo(Double.valueOf(num.doubleValue())) <= 0 ;
		}
		
		if( min != null && value instanceof BigDecimal ) {
			var num = (BigDecimal)value;
			valide =  num.compareTo(BigDecimal.valueOf(min)) >= 0;
		}
		
		if( !valide ) {
			var msgTpl = "La valeur de champ doit être supérieure ou égale à %.2f!";
			field.addMessage(Message.error(String.format(msgTpl, min)));
		}
		
		return valide;
	}
}