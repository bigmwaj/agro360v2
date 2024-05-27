package com.agro360.operation.rule.av.facture;

import java.util.List;

import org.springframework.lang.NonNull;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.FactureStatusEnumVd;

public class IsStatusInTarget extends AbstractRule<FactureBean> {

	private final List<FactureStatusEnumVd> status;

	public IsStatusInTarget(@NonNull List<FactureStatusEnumVd> status) {
		this.status = status;
	}

	@Override
	public boolean eval(ClientContext ctx, FactureBean bean) {
		var type = bean.getType().getValue();
		var status = bean.getStatus().getValue();
		var targets = StatusFlowHelper.getTargets(type, status);
		
		return targets.containsAll(this.status);
	}
}