package com.agro360.service.av;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.FactureOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class FactureService extends AbstractService {

	@Autowired
	private FactureOperation service;

	public List<FactureBean> searchAction(ClientContext ctx, Optional<FactureSearchBean> searchBean) {
		var q = searchBean.orElse(new FactureSearchBean());
		return  service.findFacturesByCriteria(ctx, q);
	}

	public void save(ClientContext ctx, FactureBean bean) {
		
		switch (bean.getAction()) {
		case CREATE:
			service.createFacture(ctx, bean);
			
			var msgTpl = "La facture <strong>%s</strong> a été créée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;
			
		case UPDATE:
			service.updateFacture(ctx, bean);
			
			msgTpl = "La facture <strong>%s</strong> a été modifiée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ANNL:
				service.annulerFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été annulée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));				
				break;
				
			case APPR:
				service.approuverFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été approuvée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case ATAP:
				service.demanderApprobationFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été envoyée en approbation avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case CLOT:
				service.cloturerFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été cloturée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case REJT:
				service.rejeterFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été rejetée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;

			default:
				break;
			}
			break;

		case DELETE:
			service.deleteFacture(ctx, bean);
			
			msgTpl = "La facture <strong>%s</strong> a été supprimée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;
			
		default:
			
		}
	}
}
