package com.agro360.operation.rule.module.stock.inventaire;

import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.common.ClientOperationEnumVd;

public class IsAjusterQuantiteRule extends AbstractRule<InventaireBean>{

	public boolean eval(ClientContext ctx, InventaireBean bean) {
		var operation = bean.getAction();
		return ClientOperationEnumVd.ACT01.equals(operation);
	}
}
