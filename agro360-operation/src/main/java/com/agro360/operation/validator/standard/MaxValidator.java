package com.agro360.operation.validator.standard;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.validator.common.Validator;

@Component
public class MaxValidator extends Validator<AbstractBean> {
	
	public MaxValidator() {
		super(null);
	}
	
	@Override
	public boolean validate(ClientContext ctx, AbstractBean bean, String fieldName) {
		var field = bean.getField(fieldName);
		var value = field.getValue();
		var max = field.getMax();
		
		var valide = true;
		
		if( max != null && value instanceof Number ) {
			var num = (Number)value;
			valide = max.compareTo(Double.valueOf(num.doubleValue())) >= 0 ;
		}
		
		if( max != null && value instanceof BigDecimal ) {
			var num = (BigDecimal)value;
			valide =  num.compareTo(BigDecimal.valueOf(max)) <= 0;
		}
		
		if( !valide ) {
			var msgTpl = "La valeur de champ doit être inférieure ou égale à %.2f!";
			field.addMessage(Message.error(String.format(msgTpl, max)));
		}
		
		return valide;
	}
}