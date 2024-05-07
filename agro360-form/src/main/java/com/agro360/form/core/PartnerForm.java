package com.agro360.form.core;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.mapper.CoreMapper;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.PartnerCategoryOperation;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.vd.core.PartnerStatusEnumVd;

@Component
public class PartnerForm {

	@Autowired
	private PartnerOperation operation;	

	@Autowired
	private PartnerCategoryOperation partnerCategoryOperation;
	
	@MetadataBeanName("core/partner")
	public PartnerBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findPartnerByCode(ctx, e))
				.orElse(new PartnerBean());
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getStatus().setValue(PartnerStatusEnumVd.ACTIVE);
		bean.getPartnerCode().setValue(null);
		bean.getPhone().setValue(null);
		bean.getEmail().setValue(null);
		
		Function<String, PartnerCategoryBean> getPartnerCat;
		getPartnerCat = e -> partnerCategoryOperation.findPartnerCategoryHierarchyFromLeaves(ctx, e);
		
		Supplier<PartnerCategoryBean> getDefaultCat;
		getDefaultCat = () -> partnerCategoryOperation.findPartnerRootCategoryHierarchy(ctx, 3);
		
		var root = copyFrom.map(getPartnerCat).orElseGet(getDefaultCat);
		bean.setCategoriesHierarchie(root);
		return bean;
	}
	
	@MetadataBeanName("core/partner")
	public PartnerBean initEditFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		var root = partnerCategoryOperation.findPartnerCategoryHierarchyFromLeaves(ctx, partnerCode);
		bean.setCategoriesHierarchie(root);
		bean.setAction(ClientOperationEnumVd.UPDATE);
		return bean;
	}

	@MetadataBeanName("core/partner")
	public PartnerBean initDeleteFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		bean.setAction(ClientOperationEnumVd.DELETE);
		return bean;
	}

	@MetadataBeanName("core/partner")
	public PartnerBean initChangeStatusFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		bean.getStatusDate().setValue(LocalDateTime.now());
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		return bean;
	}

	@MetadataBeanName("core/partner-search")
	public PartnerSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = CoreMapper.buildPartnerSearchBean();
		return bean;
	}

	public List<PartnerBean> initSearchResultBeans(ClientContext ctx, List<PartnerBean> beans) {
		return beans;
	}

}
