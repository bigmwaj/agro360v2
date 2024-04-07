package com.agro360.operation.rule.module.stock.caisse;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.stock.CaisseBean;
import com.agro360.operation.context.BeanRuleContext;
import com.agro360.operation.rule.common.IsEditableOperationRule;
import com.agro360.vd.stock.CaisseStatusEnumVd;

/**
 * Check if the root bean is editable
 */
@Component("stock/caisse/IsEditableBean")
public class IsEditableCaisseRule extends IsEditableOperationRule{

	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		var b = (CaisseBean)bean;
		var editalbe = super.apply(ctx, bean);
		return  editalbe && b.getStatus().getValue() != CaisseStatusEnumVd.FERMEE;
	}
}