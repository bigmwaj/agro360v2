package com.agro360.operation.rule.av.commande;

import java.util.List;

import org.springframework.lang.NonNull;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.CommandeStatusEnumVd;

public class IsStatusIn extends AbstractRule<CommandeBean> {
	
	private final List<CommandeStatusEnumVd> statuses;
	
	public IsStatusIn(@NonNull List<CommandeStatusEnumVd> statuses) {
		this.statuses = statuses;
	}
	
	@Override
	public boolean eval(ClientContext ctx, CommandeBean bean) {
		var status = bean.getStatus().getValue();	
		return statuses.stream().anyMatch(status::equals);
	}
}