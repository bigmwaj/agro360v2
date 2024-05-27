package com.agro360.operation.action.av.facture;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.dao.av.IFactureDao;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.vd.av.FactureStatusEnumVd;

public class FilterStatusAction extends AbstractAction<Map<Object, String>, FactureBean> {

	@Autowired
	private IFactureDao dao;
	
	@Override
	public Map<Object, String> process(ClientContext ctx, FactureBean bean) {
		var dto = dao.getReferenceById(bean.getFactureCode().getValue());
		var type = dto.getType();
		var currentStatus = dto.getStatus();
		
		return StatusFlowHelper.getTargets(type, currentStatus)
			.stream()
			.filter(Predicate.not(FactureStatusEnumVd::isInternalOnly))
			.collect(Collectors.toMap(e -> e, FactureStatusEnumVd::getLibelle));
	}
}