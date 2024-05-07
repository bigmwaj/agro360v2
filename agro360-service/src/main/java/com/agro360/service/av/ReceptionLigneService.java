package com.agro360.service.av;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.av.ReceptionLigneBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.ReceptionLigneOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class ReceptionLigneService extends AbstractService {

	@Autowired
	private ReceptionLigneOperation operation;
	
	public List<ReceptionLigneBean> findCommandeReceptions(ClientContext ctx, 
			String commandeCode) {
		return operation.findCommandeReceptions(ctx, commandeCode);
	}

	public void save(ClientContext ctx, 
			String commandeCode, List<ReceptionLigneBean> beans) {
		
		for (var bean : beans) {
			switch (bean.getAction()) {
				case CREATE:
					operation.createReceptionLigne(ctx, commandeCode, bean);
					break;
				case UPDATE:
					operation.updateReceptionLigne(ctx, commandeCode, bean);
					break;
				case DELETE:
					operation.deleteReceptionLigne(ctx, commandeCode, bean);
					break;
				case CHANGE_STATUS:
					break;
				default:
					break;
			}
		}
		
	}
}
