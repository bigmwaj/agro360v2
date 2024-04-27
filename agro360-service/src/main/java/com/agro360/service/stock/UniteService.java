package com.agro360.service.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.UniteOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class UniteService extends AbstractService {

	@Autowired
	UniteOperation operation;

	public List<UniteBean> search(ClientContext ctx, UniteSearchBean searchBean) {
		return  operation.findUnitesByCriteria(ctx, searchBean);
	}

	public void save(ClientContext ctx,  List<UniteBean> beans) {
		beans.stream().forEach(e -> save(ctx, e));
		
	}
	
	private void save(ClientContext ctx, UniteBean bean) {

		switch (bean.getAction()) {
		case CREATE:
			operation.createUnite(ctx, bean);
			
			var msgTpl = "L'unité %s a été créée avec succès!";
			ctx.success(String.format(msgTpl, bean.getUniteCode().getValue()));
			break;

		case UPDATE:
			operation.updateUnite(ctx, bean);
			
			msgTpl = "L'unité %s a été modifiée avec succès!";
			ctx.success(String.format(msgTpl, bean.getUniteCode().getValue()));
			break;

		case DELETE:
			operation.deleteUnite(ctx, bean);
			
			msgTpl = "L'unité %s a été supprimée avec succès!";
			ctx.success(String.format(msgTpl, bean.getUniteCode().getValue()));
			break;
			
		default:
			
		}
	}
}
