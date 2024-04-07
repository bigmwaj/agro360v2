package com.agro360.service.finance;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.finance.TransactionSearchBean;
import com.agro360.bo.bean.finance.TransfertBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.TransactionOperation;
import com.agro360.service.common.AbstractService;

@Service
public class TransactionService extends AbstractService {

	@Autowired
	TransactionOperation service;

	public List<TransactionBean> searchAction(ClientContext ctx, Optional<TransactionSearchBean> searchForm) {	
		return service.findTransactionsByCriteria(ctx, searchForm.orElse(new TransactionSearchBean()));
	}
	
	public void saveAction(ClientContext ctx, TransactionBean bean) {
		
		switch (bean.getAction()) {
		case CREATE:
			service.createTransaction(ctx, bean);
			break;
			
		case UPDATE:
			service.updateTransaction(ctx, bean);
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ANNULEE:
				service.annulerTransaction(ctx, bean);				
				break;
				
			case APPROUVEE:
				service.approuverTransaction(ctx, bean);
				break;
				
			case CLOTUREE:
				service.clotureTransaction(ctx, bean);
				break;

			default:
				break;
			}
			break;

		case DELETE:
			service.deleteTransaction(ctx, bean);
			break;
			
		default:
			
		};
	}

	public void transfert(ClientContext ctx, TransfertBean bean) {
		service.transfert(ctx, bean);
		
	}

}
