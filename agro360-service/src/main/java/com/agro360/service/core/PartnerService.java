package com.agro360.service.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import com.agro360.service.common.ServiceValidationException;
import com.agro360.service.common.UserInput;

@Transactional(rollbackFor = Exception.class)
@Service
public class PartnerService extends AbstractService {

	@Autowired
	private PartnerOperation operation;
	
	@Autowired
	private PartnerCategoryOperation partnerCategoryOperation;

	public List<PartnerBean> search(ClientContext ctx, PartnerSearchBean searchForm) {	
		return operation.findPartnersByCriteria(ctx, searchForm);
	}
	
	private void retrieveSelectedCategoryCodes(Set<String> categoryCodes, PartnerCategoryBean bean){
		if( Boolean.TRUE.equals(bean.getSelected().getValue()) ) {
			categoryCodes.add(bean.getCategoryCode().getValue());			
		}
		
		for ( var b : bean.getChildren()) {
			retrieveSelectedCategoryCodes(categoryCodes, b);
		}
	}
	
	private Collection<String> retrieveSelectedCategoryCodes(PartnerCategoryBean bean){
		Set<String> categoryCodes = new HashSet<>();
		retrieveSelectedCategoryCodes(categoryCodes, bean);
		return categoryCodes;
	}
	
	@UserInput(validatorBeanName = "", metadataBeanName = "")
	public void save(ClientContext ctx, PartnerBean bean) throws ServiceValidationException{
		switch (bean.getAction()) {
		case CREATE:
			operation.createPartner(ctx, bean);
			var categoryCodes = retrieveSelectedCategoryCodes(bean.getCategoriesHierarchie());
			partnerCategoryOperation.syncAssignments(ctx,  bean, categoryCodes);
			ctx.success("Partenaire créé avec succès!");
			break;
			
		case UPDATE:
			operation.updatePartner(ctx, bean);
			categoryCodes = retrieveSelectedCategoryCodes(bean.getCategoriesHierarchie());
			partnerCategoryOperation.syncAssignments(ctx, bean, categoryCodes);
			ctx.success("Partenaire modifié avec succès!");
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ENABLED:
				ctx.success("Partenaire activé avec succès!");
				operation.activatePartner(ctx, bean);
				
				break;
			case DISABLED:
				ctx.success("Partenaire désactivé avec succès!");
				operation.deactivatePartner(ctx, bean);
				
				break;

			default:
				break;
			}
			break;

		case DELETE:
			partnerCategoryOperation.deleteAll(ctx, bean);
			ctx.success("Partenaire supprimé avec succès!");
			operation.deletePartner(ctx, bean);
			break;
			
		default:
			
		}
	}

	public PartnerBean findPartner(ClientContext ctx, String value) {
		return operation.findPartnerByCode(ctx, value);
	}

}
