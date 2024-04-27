package com.agro360.service.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class MagasinService extends AbstractService {

	@Autowired
	MagasinOperation operation;
	
	public List<MagasinBean> search(ClientContext ctx, MagasinSearchBean searchBean) {
		return operation.findMagasinsByCriteria(ctx, searchBean);
	}

	public void save(ClientContext ctx, MagasinBean bean) {
		switch (bean.getAction()) {
		case CREATE:
			operation.createMagasin(ctx, bean);
			
			var msgTpl = "Le magasin %s a été créé avec succès!";
			ctx.success(String.format(msgTpl, bean.getMagasinCode().getValue()));
			break;

		case UPDATE:
			operation.updateMagasin(ctx, bean);
			
			msgTpl = "Le magasin %s a été modifié avec succès!";
			ctx.success(String.format(msgTpl, bean.getMagasinCode().getValue()));
			break;

		case DELETE:
			operation.deleteMagasin(ctx, bean);
			
			msgTpl = "Le magasin %s a été supprimé avec succès!";
			ctx.success(String.format(msgTpl, bean.getMagasinCode().getValue()));
			break;
			
		default:
		}
		
	}

}
