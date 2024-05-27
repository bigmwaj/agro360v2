package com.agro360.operation.rule.finance.transaction;

import java.util.List;

import org.springframework.lang.NonNull;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.finance.TransactionStatusEnumVd;

public class IsStatusInTarget extends AbstractRule<TransactionBean> {
	
	private final List<TransactionStatusEnumVd> status;
	
	public IsStatusInTarget(@NonNull List<TransactionStatusEnumVd> status) {
		this.status = status;
	}
	
	@Override
	public boolean eval(ClientContext ctx, TransactionBean bean) {
		var type = bean.getType().getValue();
		var status = bean.getStatus().getValue();
		var targets = StatusFlowHelper.getTargets(type, status);
		
		return targets.containsAll(this.status);
	}
}