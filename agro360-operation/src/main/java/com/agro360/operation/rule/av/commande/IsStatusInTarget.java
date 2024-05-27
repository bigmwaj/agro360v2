package com.agro360.operation.rule.av.commande;

import java.util.List;

import org.springframework.lang.NonNull;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeStatusEnumVd;

public class IsStatusInTarget extends AbstractRule<CommandeBean> {
	
	private final List<CommandeStatusEnumVd> status;
	
	public IsStatusInTarget(@NonNull List<CommandeStatusEnumVd> status) {
		this.status = status;
	}
	
	@Override
	public boolean eval(ClientContext ctx, CommandeBean bean) {
		var type = bean.getType().getValue();
		var status = bean.getStatus().getValue();
		var targets = StatusFlowHelper.getTargets(type, status);
		
		return targets.containsAll(this.status);
	}
}