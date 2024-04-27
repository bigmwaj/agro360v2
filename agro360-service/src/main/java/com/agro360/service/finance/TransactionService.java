package com.agro360.service.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.finance.TransactionSearchBean;
import com.agro360.bo.bean.finance.TransfertBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.TransactionOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class TransactionService extends AbstractService {

	@Autowired
	private TransactionOperation operation;

	public List<TransactionBean> search(ClientContext ctx, TransactionSearchBean searchForm) {	
		return operation.findTransactionsByCriteria(ctx, searchForm);
	}
	
	public void save(ClientContext ctx, TransactionBean bean) {
		
		switch (bean.getAction()) {
		case CREATE:
			operation.createTransaction(ctx, bean);
			break;
			
		case UPDATE:
			operation.updateTransaction(ctx, bean);
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ANNULEE:
				operation.annulerTransaction(ctx, bean);				
				break;
				
			case APPROUVEE:
				operation.approuverTransaction(ctx, bean);
				break;
				
			case CLOTUREE:
				operation.clotureTransaction(ctx, bean);
				break;

			default:
				break;
			}
			break;

		case DELETE:
			operation.deleteTransaction(ctx, bean);
			break;
			
		default:
			
		};
		
	}

	public List<TransactionBean> transfert(ClientContext ctx, TransfertBean bean) {
		var result = operation.transfert(ctx, bean);
		var montant = bean.getMontant().getValue();
		var source = bean.getCompteSource().getCompteCode().getValue();
		var cible = bean.getCompteCible().getCompteCode().getValue();
		var partner = bean.getPartner().getPartnerName().getValue();
		var msgTpl = "Le transfert de %.2f du compte %s au compte %s au profit de %s effectué avec succès!";
		ctx.success(String.format(msgTpl, montant, source, cible,  partner));
		
		return result;
	}

	public TransactionBean findTransaction(ClientContext ctx, String value) {
		return operation.findTransactionByCode(ctx, value);
	}

}
