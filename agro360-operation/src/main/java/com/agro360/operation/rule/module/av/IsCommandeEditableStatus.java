package com.agro360.operation.rule.module.av;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.operation.rule.exception.RuleException;
import com.agro360.vd.av.CommandeStatusEnumVd;

@Component
public class IsCommandeEditableStatus extends AbstractRule {
	
	@Override
	public boolean eval(ClientContext ctx, AbstractBean bean) {
		if( !(bean instanceof CommandeBean) && !(bean instanceof LigneBean) ) {
			throw new RuleException("Cette règle ne s'applique qu'aux commandes et aux lignes de commande");
		}
		CommandeBean commande;
		if( bean instanceof LigneBean ) {
			getLogger().debug("Le owner est {}", bean.getOwnerBean().getClass());
			commande = (CommandeBean) bean.getOwnerBean();
		}else {
			commande = (CommandeBean) bean;
		}
		var status = commande.getStatus().getValue();		
		return CommandeStatusEnumVd.BRLN.equals(status) || CommandeStatusEnumVd.REJT.equals(status);
	}
}