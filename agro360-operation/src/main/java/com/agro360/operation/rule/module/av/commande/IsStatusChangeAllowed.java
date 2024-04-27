package com.agro360.operation.rule.module.av.commande;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeStatusEnumVd;

public class IsStatusChangeAllowed extends AbstractRule<CommandeBean> {
	
	@Override
	public boolean eval(ClientContext ctx, CommandeBean bean) {
		var status = bean.getStatus().getValue();	
		var type = bean.getType().getValue();
		switch (type) {
		case ACHAT:
			return !CommandeStatusEnumVd.RECT.equals(status) && !CommandeStatusEnumVd.ANNL.equals(status);
		case VENTE:
			return !CommandeStatusEnumVd.APPR.equals(status) && !CommandeStatusEnumVd.ANNL.equals(status);

		default:
			return false;
		}
	}
}