package com.agro360.operation.rule.av.facture;

import java.util.List;

import org.springframework.lang.NonNull;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.FactureStatusEnumVd;

public class IsStatusIn extends AbstractRule<FactureBean> {
	
	private final List<FactureStatusEnumVd> statuses;
	
	public IsStatusIn(@NonNull List<FactureStatusEnumVd> statuses) {
		this.statuses = statuses;
	}
	
	@Override
	public boolean eval(ClientContext ctx, FactureBean bean) {
		var status = bean.getStatus().getValue();	
		return statuses.stream().anyMatch(status::equals);
	}
}