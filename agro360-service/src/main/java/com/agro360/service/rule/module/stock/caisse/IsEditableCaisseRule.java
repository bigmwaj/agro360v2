package com.agro360.service.rule.module.stock.caisse;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.context.BeanRuleContext;
import com.agro360.service.rule.common.IsEditableOperationRule;
import com.agro360.vd.stock.StatusCaisseEnumVd;

/**
 * Check if the root bean is editable
 */
@Component("stock/caisse/IsEditableBean")
public class IsEditableCaisseRule extends IsEditableOperationRule{

	public boolean apply(BeanRuleContext ctx, AbstractBean bean) {
		var b = (CaisseBean)bean;
		var editalbe = super.apply(ctx, bean);
		return  editalbe && b.getStatus().getValue() != StatusCaisseEnumVd.FERMEE;
	}
}