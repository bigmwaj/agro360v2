package com.agro360.operation.rule.module.av.ligne;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeStatusEnumVd;

public class IsCommandeEditableStatus extends AbstractRule<LigneBean> {
	
	@Override
	public boolean eval(ClientContext ctx, LigneBean bean) {
		var commande = (CommandeBean) bean.getOwnerBean();
		var status = commande.getStatus().getValue();		
		return CommandeStatusEnumVd.BRLN.equals(status) || CommandeStatusEnumVd.REJT.equals(status);
	}
}