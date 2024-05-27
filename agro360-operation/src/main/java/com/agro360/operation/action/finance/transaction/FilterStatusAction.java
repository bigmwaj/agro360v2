package com.agro360.operation.action.finance.transaction;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.dao.finance.ITransactionDao;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.helper.StatusFlowHelper;
import com.agro360.vd.finance.TransactionStatusEnumVd;

public class FilterStatusAction extends AbstractAction<Map<Object, String>, TransactionBean> {

	@Autowired
	private ITransactionDao dao;
	
	@Override
	public Map<Object, String> process(ClientContext ctx, TransactionBean bean) {
		var dto = dao.getReferenceById(bean.getTransactionCode().getValue());
		var type = dto.getType();
		var currentStatus = dto.getStatus();
		
		return StatusFlowHelper.getTargets(type, currentStatus)
				.stream()
				.filter(Predicate.not(TransactionStatusEnumVd::isInternalOnly))
				.collect(Collectors.toMap(e -> e, TransactionStatusEnumVd::getLibelle));
	}
}