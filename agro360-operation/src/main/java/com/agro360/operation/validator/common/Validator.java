package com.agro360.operation.validator.common;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.action.common.SetValueAction;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

public class Validator<B extends AbstractBean> {

	/**
	 * Règle à exécuter
	 */
	private final AbstractRule<B> rule;
	
	/**
	 * Si la règle rule échoue, on utilise cette action pour calculer le message à afficher
	 */
	private final AbstractAction<String, B> ifFail;	
	
	public Validator(AbstractRule<B> rule, AbstractAction<String, B> ifFail) {
		this.rule = rule;
		this.ifFail = ifFail;
	}
	
	public Validator(AbstractRule<B> rule, String message) {
		this.rule = rule;
		this.ifFail = new SetValueAction<String, B>(message);
	}
	
	public Validator(AbstractRule<B> rule) {
		this.rule = rule;
		this.ifFail = new SetValueAction<String, B>("Echec validation");
	}
	
	public boolean validate(ClientContext ctx, B bean, String fieldName) {
		if( !rule.eval(ctx, bean) ) {
			var msg = ifFail.process(ctx, bean);
			var field = bean.getField(fieldName);
			field.addMessage(Message.error(msg));
			
			return false;
		}
		
		return true;
	}
}
