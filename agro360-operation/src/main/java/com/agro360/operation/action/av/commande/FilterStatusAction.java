package com.agro360.operation.action.av.commande;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.dao.av.ICommandeDao;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.vd.av.CommandeStatusEnumVd;

public class FilterStatusAction extends AbstractAction<Map<Object, String>, CommandeBean> {

	@Autowired
	private ICommandeDao dao;
	
	@Override
	public Map<Object, String> process(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());
		var type = dto.getType();
		var currentStatus = dto.getStatus();
		
		return StatusFlowHelper.getTargets(type, currentStatus)
			.stream()
			.filter(Predicate.not(CommandeStatusEnumVd::isInternalOnly))
			.collect(Collectors.toMap(e -> e, CommandeStatusEnumVd::getLibelle));
	}
}