package com.agro360.service.av;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.FactureOperation;
import com.agro360.service.common.AbstractService;

@Service
public class FactureService extends AbstractService {

	@Autowired
	private FactureOperation service;

	public List<FactureBean> searchAction(ClientContext ctx, Optional<FactureSearchBean> searchBean) {
		var q = searchBean.orElse(new FactureSearchBean());
		return  service.findFacturesByCriteria(ctx, q);
	}

	public void saveAction(ClientContext ctx, FactureBean bean) {
		
		switch (bean.getAction()) {
		case CREATE:
			service.createFacture(ctx, bean);
			break;
			
		case UPDATE:
			service.updateFacture(ctx, bean);
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ANNL:
				service.annulerFacture(ctx, bean);				
				break;
				
			case APPR:
				service.approuverFacture(ctx, bean);
				break;
				
			case ATAP:
				service.demanderApprobationFacture(ctx, bean);
				break;
				
			case CLOT:
				service.cloturerFacture(ctx, bean);
				break;
				
			case REJT:
				service.rejeterFacture(ctx, bean);
				break;

			default:
				break;
			}
			break;

		case DELETE:
			service.deleteFacture(ctx, bean);
			break;
			
		default:
			
		}
	}
}
