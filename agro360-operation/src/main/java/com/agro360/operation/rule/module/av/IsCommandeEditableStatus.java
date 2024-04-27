package com.agro360.operation.rule.module.av;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeStatusEnumVd;

public class IsCommandeEditableStatus extends AbstractRule<CommandeBean> {
	
	@Override
	public boolean eval(ClientContext ctx, CommandeBean bean) {
		var status = bean.getStatus().getValue();		
		return CommandeStatusEnumVd.BRLN.equals(status) || CommandeStatusEnumVd.REJT.equals(status);
	}
}