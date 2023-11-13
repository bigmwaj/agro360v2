package com.agro360.service.rule.constraint.stock.caisse;

import org.springframework.stereotype.Component;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.context.BeanContext;
import com.agro360.service.rule.constraint.common.AbstractConstraint;
import com.agro360.vd.stock.StatusCaisseEnumVd;

/**
 * Check if the root bean is editable
 */
@Component("stock/caisse/IsEditableBean")
public class IsEditable extends AbstractConstraint{

	public boolean apply(BeanContext ctx, AbstractBean bean) {
		var caisseBean = (CaisseBean)bean;
		
		return caisseBean.getStatus().getValue() != StatusCaisseEnumVd.FERMEE;
	}
}