package com.agro360.operation.rule.module.av.commande;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;

public class IsPaiementCommandeAllowed extends AbstractRule<CommandeBean> {
	
	@Override
	public boolean eval(ClientContext ctx, CommandeBean bean) {
		var status = bean.getStatus().getValue();	
		var type = bean.getType().getValue();
		return CommandeTypeEnumVd.VENTE.equals(type)
		&& (CommandeStatusEnumVd.BRLN.equals(status) || CommandeStatusEnumVd.PAYT.equals(status));
	}
}