package com.agro360.operation.action.finance.transaction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.dao.finance.ITransactionDao;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.finance.TransactionStatusEnumVd;

public class FilterStatusAction extends AbstractAction<Map<Object, String>, TransactionBean> {

	@Autowired
	private ITransactionDao dao;
	
	@Override
	public Map<Object, String> process(ClientContext ctx, TransactionBean bean) {
		var dto = dao.getReferenceById(bean.getTransactionCode().getValue());
		var currentStatus = dto.getStatus();
		Map<Object, String> valueOptions = new HashMap<>();
		
		switch (currentStatus) {
		case ENCOURS:
			valueOptions.put(TransactionStatusEnumVd.APPROUVEE, TransactionStatusEnumVd.APPROUVEE.getLibelle());
			break;
		case APPROUVEE:
			valueOptions.put(TransactionStatusEnumVd.CLOTUREE, TransactionStatusEnumVd.CLOTUREE.getLibelle());
			break;

		default:
			break;
		}
		
		return valueOptions;
	}
}