package com.agro360.service.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.CasierOperation;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class MagasinService extends AbstractService {

	@Autowired
	MagasinOperation operation;
	
	@Autowired
	CasierOperation casierOperation;
	
	public List<MagasinBean> search(ClientContext ctx, Optional<MagasinSearchBean> searchBean) {
		return operation.findMagasinsByCriteria(ctx, searchBean.orElse(new MagasinSearchBean()));
	}

	public void save(ClientContext ctx, MagasinBean bean) {
		switch (bean.getAction()) {
		case CREATE:
			operation.createMagasin(ctx, bean);
			addAllCasiersMagasin(ctx, bean);
			
			var msgTpl = "Le magasin %s a été créé avec succès!";
			ctx.success(String.format(msgTpl, bean.getMagasinCode().getValue()));
			break;

		case UPDATE:
			operation.updateMagasin(ctx, bean);
			syncAllCasiersMagasin(ctx, bean);
			
			msgTpl = "Le magasin %s a été modifié avec succès!";
			ctx.success(String.format(msgTpl, bean.getMagasinCode().getValue()));
			break;

		case DELETE:
			deleteAllCasiersMagasin(ctx, bean);
			operation.deleteMagasin(ctx, bean);
			
			msgTpl = "Le magasin %s a été supprimé avec succès!";
			ctx.success(String.format(msgTpl, bean.getMagasinCode().getValue()));
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
