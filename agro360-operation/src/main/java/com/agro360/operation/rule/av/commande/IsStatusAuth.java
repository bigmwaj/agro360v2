package com.agro360.operation.rule.av.commande;

import java.util.function.Predicate;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeStatusEnumVd;

public class IsStatusAuth extends AbstractRule<CommandeBean> {
	
	@Override
	public boolean eval(ClientContext ctx, CommandeBean bean) {
		var type = bean.getType().getValue();
		var status = bean.getStatus().getValue();
		return !status.isInternalOnly() && StatusFlowHelper.getTargets(type, status)
				.stream()
				.filter(Predicate.not(CommandeStatusEnumVd::isInternalOnly))
				.count() > 0;
	}
}