package com.agro360.operation.rule.module.finance.transaction;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.finance.TransactionStatusEnumVd;

public class IsStatusChangeAllowed extends AbstractRule<TransactionBean> {
	
	@Override
	public boolean eval(ClientContext ctx, TransactionBean bean) {
		var status = bean.getStatus().getValue();	
		return !TransactionStatusEnumVd.ANNULEE.equals(status)
				&& !TransactionStatusEnumVd.CLOTUREE.equals(status);
	}
}