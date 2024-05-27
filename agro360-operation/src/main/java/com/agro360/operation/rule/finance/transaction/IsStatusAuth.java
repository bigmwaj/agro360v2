package com.agro360.operation.rule.finance.transaction;

import java.util.function.Predicate;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.finance.TransactionStatusEnumVd;

public class IsStatusAuth extends AbstractRule<TransactionBean> {

	@Override
	public boolean eval(ClientContext ctx, TransactionBean bean) {
		var type = bean.getType().getValue();
		var status = bean.getStatus().getValue();
		return !status.isInternalOnly() && StatusFlowHelper.getTargets(type, status)
				.stream()
				.filter(Predicate.not(TransactionStatusEnumVd::isInternalOnly))
				.count() > 0;
	}
}