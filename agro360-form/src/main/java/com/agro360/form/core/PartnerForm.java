package com.agro360.form.core;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.mapper.core.PartnerMapper;
import com.agro360.dto.core.PartnerDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.PartnerCategoryOperation;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.core.PartnerStatusEnumVd;

@Component
public class PartnerForm {

	@Autowired
	private PartnerMapper mapper;

	@Autowired
	private PartnerOperation operation;	

	@Autowired
	private PartnerCategoryOperation partnerCategoryOperation;

	public PartnerBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = mapper.map(new PartnerDto());
		var root = partnerCategoryOperation.findPartnerRootCategoryHierarchy(ctx, 3);
		bean.setCategoriesHierarchie(root);
		
		bean.setAction(EditActionEnumVd.CREATE);
		
		bean.getStatus().setValue(PartnerStatusEnumVd.ACTIVE);
		bean.getStatus().setEditable(false);
		bean.getPartnerCode().setRequired(true);
		bean.getPartnerType().setRequired(true);
		bean.getPhone().setRequired(true);
		bean.getEmail().setRequired(true);
		return bean;
	}
	
	public PartnerBean initEditFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		var cat = partnerCategoryOperation.findPartnerCategoryHierarchyFromLeaves(ctx, partnerCode);
		bean.setCategoriesHierarchie(cat);
		
		bean.setAction(EditActionEnumVd.UPDATE);
		
		bean.getPartnerCode().setRequired(true);
		bean.getPartnerType().setRequired(true);
		bean.getPhone().setRequired(true);
		bean.getEmail().setRequired(true);
		bean.getStatus().setEditable(false);
		return bean;
	}

	public PartnerBean initDeleteFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		
		bean.setAction(EditActionEnumVd.DELETE);
		
		bean.getPartnerCode().setRequired(true);
		return bean;
	}

	public PartnerBean initChangeStatusFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		
		bean.setAction(EditActionEnumVd.CHANGE_STATUS);
		
		bean.getPartnerCode().setRequired(true);
		bean.getStatus().setRequired(true);
		return bean;
	}

	public PartnerSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = mapper.mapToSearchBean();
		return bean;
	}

}
