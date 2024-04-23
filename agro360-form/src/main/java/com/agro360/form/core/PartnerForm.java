package com.agro360.form.core;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.mapper.CoreMapper;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.PartnerCategoryOperation;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.operation.metadata.BeanMetadataConfig;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.vd.core.PartnerStatusEnumVd;

@Component
public class PartnerForm {

	@Autowired
	private PartnerOperation operation;	

	@Autowired
	private PartnerCategoryOperation partnerCategoryOperation;
	
	@Qualifier("core/partner")
	@Autowired
	private BeanMetadataConfig partnerMetadataConfig;
	
	@Qualifier("core/partner-category")
	@Autowired
	private BeanMetadataConfig partnerCategoryMetadataConfig;

	public PartnerBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = new PartnerBean();
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getStatus().setValue(PartnerStatusEnumVd.ACTIVE);
		var root = partnerCategoryOperation.findPartnerRootCategoryHierarchy(ctx, 3);
		bean.setCategoriesHierarchie(root);
		
		partnerMetadataConfig.applyMetadata(ctx, bean);
		applyMetadataConfig(ctx, root);
		
		return bean;
	}
	
	private void applyMetadataConfig(ClientContext ctx, PartnerCategoryBean bean) {
		partnerCategoryMetadataConfig.applyMetadata(ctx, bean);
		for (var child : bean.getChildren()) {
			applyMetadataConfig(ctx, child);
		}
	}
	
	public PartnerBean initEditFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		var root = partnerCategoryOperation.findPartnerCategoryHierarchyFromLeaves(ctx, partnerCode);
		bean.setCategoriesHierarchie(root);
		bean.setAction(ClientOperationEnumVd.UPDATE);
		
		partnerMetadataConfig.applyMetadata(ctx, bean);
		applyMetadataConfig(ctx, root);
		return bean;
	}

	public PartnerBean initDeleteFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		
		bean.setAction(ClientOperationEnumVd.DELETE);
		partnerMetadataConfig.applyMetadata(ctx, bean);
		return bean;
	}

	public PartnerBean initChangeStatusFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		
		bean.getStatusDate().setValue(LocalDateTime.now());
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		partnerMetadataConfig.applyMetadata(ctx, bean);
		return bean;
	}

	public PartnerSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = CoreMapper.buildPartnerSearchBean();
		return bean;
	}

}
