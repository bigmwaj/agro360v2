package com.agro360.operation.rule.finance.transaction;

import java.util.List;

import org.springframework.lang.NonNull;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.finance.TransactionStatusEnumVd;

public class IsStatusIn extends AbstractRule<TransactionBean> {
	
	private final List<TransactionStatusEnumVd> statuses;
	
	public IsStatusIn(@NonNull List<TransactionStatusEnumVd> statuses) {
		this.statuses = statuses;
	}
	
	@Override
	public boolean eval(ClientContext ctx, TransactionBean bean) {
		var status = bean.getStatus().getValue();	
		return statuses.stream().anyMatch(status::equals);
	}
}