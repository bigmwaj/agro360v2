package com.agro360.operation.rule.finance.transaction;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.finance.TransactionStatusEnumVd;

public class IsDeleteAllowed extends AbstractRule<TransactionBean> {
	
	@Override
	public boolean eval(ClientContext ctx, TransactionBean bean) {
		var status = bean.getStatus().getValue();	
		return TransactionStatusEnumVd.ENCOURS.equals(status);
	}
}