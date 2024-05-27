package com.agro360.operation.rule.av.facture;

import java.util.function.Predicate;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.vd.av.FactureStatusEnumVd;

public class IsStatusAuth extends AbstractRule<FactureBean> {

	@Override
	public boolean eval(ClientContext ctx, FactureBean bean) {
		var type = bean.getType().getValue();
		var status = bean.getStatus().getValue();
		return StatusFlowHelper.getTargets(type, status)
				.stream()
				.filter(Predicate.not(FactureStatusEnumVd::isInternalOnly))
				.count() > 0;
	}
}