package com.agro360.operation.rule.module.av.commande;

import org.springframework.lang.NonNull;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeTypeEnumVd;

public class IsTypeEquals extends AbstractRule<CommandeBean> {
	
	private final CommandeTypeEnumVd type;
	
	public IsTypeEquals(@NonNull CommandeTypeEnumVd type) {
		this.type = type;
	}
	
	@Override
	public boolean eval(ClientContext ctx, CommandeBean bean) {
		var type = bean.getType().getValue();
		
		return this.type.equals(type);
	}
}