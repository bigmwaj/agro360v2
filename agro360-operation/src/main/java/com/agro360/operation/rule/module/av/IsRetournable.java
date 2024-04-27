package com.agro360.operation.rule.module.av;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;

@Component
public class IsRetournable extends AbstractRule<LigneBean> {
	
	@Override
	public boolean eval(ClientContext ctx, LigneBean bean) {
		var commande = (CommandeBean) bean.getOwnerBean();
		var status = commande.getStatus().getValue();	
		var type = commande.getType().getValue();
		return CommandeTypeEnumVd.VENTE.equals(type) && ( CommandeStatusEnumVd.APPR.equals(status));
	}
}