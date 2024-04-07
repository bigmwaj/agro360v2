package com.agro360.service.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.CasierOperation;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.service.common.AbstractService;

@Service
public class MagasinService extends AbstractService {

	@Autowired
	MagasinOperation service;
	
	@Autowired
	CasierOperation casierOperation;
	
	public List<MagasinBean> searchAction(ClientContext ctx, Optional<MagasinSearchBean> searchBean) {
		return service.findMagasinsByCriteria(ctx, searchBean.orElse(new MagasinSearchBean()));
	}

	public void saveAction(ClientContext ctx, MagasinBean bean) {
		switch (bean.getAction()) {
		case CREATE:
			service.createMagasin(ctx, bean);
			addAllCasiersMagasin(ctx, bean);
			break;

		case UPDATE:
			service.updateMagasin(ctx, bean);
			syncAllCasiersMagasin(ctx, bean);
			break;

		case DELETE:
			deleteAllCasiersMagasin(ctx, bean);
			service.deleteMagasin(ctx, bean);
			break;
			
		default:
		}
		
	}
	
	private void addAllCasiersMagasin(ClientContext ctx, MagasinBean bean) {
		for(var casier : bean.getCasiers()) {
			casierOperation.createCasier(ctx, bean, casier);
		}
	}
	
	private void deleteAllCasiersMagasin(ClientContext ctx, MagasinBean bean) {
		for(var casier : bean.getCasiers()) {
			casierOperation.deleteCasier(ctx, bean, casier);
		}
	}
	
	private void syncAllCasiersMagasin(ClientContext ctx, MagasinBean bean) {
		for(var casier : bean.getCasiers()) {
			switch (casier.getAction()) {
			case CREATE:
				casierOperation.createCasier(ctx, bean, casier);
				break;
				
			case UPDATE:
				casierOperation.updateCasier(ctx, bean, casier);
				break;
				
			case DELETE:
				casierOperation.deleteCasier(ctx, bean, casier);
				break;

			default:
				break;
			}
		}
	}
}
