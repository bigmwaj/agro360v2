package com.agro360.service.av;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.av.RetourLigneBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.RetourLigneOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class RetourLigneService extends AbstractService {

	@Autowired
	private RetourLigneOperation operation;
	
	public List<RetourLigneBean> findCommandeRetours(ClientContext ctx, 
			String commandeCode) {
		return operation.findCommandeRetours(ctx, commandeCode);
	}

	public void save(ClientContext ctx, 
			String commandeCode, List<RetourLigneBean> beans) {
		
		for (var bean : beans) {
			switch (bean.getAction()) {
				case CREATE:
					operation.createRetourLigne(ctx, commandeCode, bean);
					break;
				case UPDATE:
					operation.updateRetourLigne(ctx, commandeCode,bean);
					break;
				case DELETE:
					operation.deleteRetourLigne(ctx, commandeCode,bean);
					break;
				case CHANGE_STATUS:
					break;
				default:
					break;
			}
		}
		
	}
}
