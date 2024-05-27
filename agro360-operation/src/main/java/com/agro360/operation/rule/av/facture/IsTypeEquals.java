package com.agro360.operation.rule.av.facture;

import org.springframework.lang.NonNull;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.FactureTypeEnumVd;

public class IsTypeEquals extends AbstractRule<FactureBean> {
	
	private final FactureTypeEnumVd type;
	
	public IsTypeEquals(@NonNull FactureTypeEnumVd type) {
		this.type = type;
	}
	
	@Override
	public boolean eval(ClientContext ctx, FactureBean bean) {
		var type = bean.getType().getValue();
		
		return this.type.equals(type);
	}
}