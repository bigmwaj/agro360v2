package com.agro360.operation.rule.module.av;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.operation.rule.exception.RuleException;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;

@Component
public class IsRetournable extends AbstractRule {
	
	@Override
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		if( !(bean instanceof LigneBean) ) {
			throw new RuleException("Cette règle ne s'applique qu'aux lignes de commande");
		}
		CommandeBean commande;
		commande = (CommandeBean) bean.getOwnerBean();
		var status = commande.getStatus().getValue();	
		var type = commande.getType().getValue();
		return CommandeTypeEnumVd.VENTE.equals(type) && ( CommandeStatusEnumVd.APPR.equals(status));
	}
}