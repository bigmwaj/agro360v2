package com.agro360.service.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.PartnerCategoryOperation;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class PartnerService extends AbstractService {

	@Autowired
	private PartnerOperation service;
	
	@Autowired
	private PartnerCategoryOperation partnerCategoryOperation;

	public List<PartnerBean> searchAction(ClientContext ctx, Optional<PartnerSearchBean> searchForm) {	
		return service.findPartnerByCriteria(ctx, searchForm.orElse(new PartnerSearchBean()));
	}
	
	private void retrieveSelectedCategoryCodes(Set<String> categoryCodes, PartnerCategoryBean bean){
		if( Boolean.TRUE.equals(bean.getSelected().getValue()) ) {
			categoryCodes.add(bean.getCategoryCode().getValue());			
		}
		
		for (PartnerCategoryBean b : bean.getChildren()) {
			retrieveSelectedCategoryCodes(categoryCodes, b);
		}
	}
	
	private Collection<String> retrieveSelectedCategoryCodes(PartnerCategoryBean bean){
		Set<String> categoryCodes = new HashSet<>();
		retrieveSelectedCategoryCodes(categoryCodes, bean);
		return categoryCodes;
	}
	
	public void saveAction(ClientContext ctx, PartnerBean bean) {
		
		switch (bean.getAction()) {
		case CREATE:
			service.createPartner(ctx, bean);
			var categoryCodes = retrieveSelectedCategoryCodes(bean.getCategoriesHierarchie());
			partnerCategoryOperation.syncPartnerCategories(ctx,  bean, categoryCodes);
			break;
			
		case UPDATE:
			service.updatePartner(ctx, bean);
			categoryCodes = retrieveSelectedCategoryCodes(bean.getCategoriesHierarchie());
			partnerCategoryOperation.syncPartnerCategories(ctx, bean, categoryCodes);
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ACTIVE:
				service.activatePartner(ctx, bean);
				
				break;
			case INACTIVE:
				service.deactivatePartner(ctx, bean);
				
				break;

			default:
				break;
			}
			break;

		case DELETE:
			partnerCategoryOperation.deleteAllPartnerCategories(ctx, bean);
			service.deletePartner(ctx, bean);
			break;
			
		default:
			
		}
	}

}
