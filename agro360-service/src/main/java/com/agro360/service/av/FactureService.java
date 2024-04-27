package com.agro360.service.av;

import java.util.List;

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
	private FactureOperation operation;

	public List<FactureBean> search(ClientContext ctx, FactureSearchBean searchBean) {
		return  operation.findFacturesByCriteria(ctx, searchBean);
	}

	public void save(ClientContext ctx, FactureBean bean) {
		
		switch (bean.getAction()) {
		case CREATE:
			operation.createFacture(ctx, bean);
			
			var msgTpl = "La facture <strong>%s</strong> a été créée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;
			
		case UPDATE:
			operation.updateFacture(ctx, bean);
			
			msgTpl = "La facture <strong>%s</strong> a été modifiée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ANNL:
				operation.annulerFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été annulée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));				
				break;
				
			case APPR:
				operation.approuverFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été approuvée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case ATAP:
				operation.demanderApprobationFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été envoyée en approbation avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case CLOT:
				operation.cloturerFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été cloturée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;
				
			case REJT:
				operation.rejeterFacture(ctx, bean);
				
				msgTpl = "La facture <strong>%s</strong> a été rejetée avec succès!";
				ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
				break;

			default:
				break;
			}
			break;

		case DELETE:
			operation.deleteFacture(ctx, bean);
			
			msgTpl = "La facture <strong>%s</strong> a été supprimée avec succès!";
			ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
			break;
			
		default:
			
		}
	}

	public FactureBean findFacture(ClientContext ctx, String value) {
		return operation.findFactureByCode(ctx, value);
	}
}
