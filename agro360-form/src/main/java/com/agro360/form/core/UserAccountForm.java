package com.agro360.form.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.UserAccountOperation;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.vd.core.UserAccountStatusEnumVd;

@Component
public class UserAccountForm {

	@Autowired
	private UserAccountOperation operation;	

	public UserAccountBean initCreateFormBean(ClientContext ctx) {
		var bean = new UserAccountBean();
		
		bean.setAction(ClientOperationEnumVd.CREATE);
		
		bean.getStatus().setValue(UserAccountStatusEnumVd.ACTIVE);
		bean.getStatus().setEditable(false);
		bean.getPartnerCode().setRequired(true);
		return bean;
	}
	
	public UserAccountBean initEditFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		bean.setAction(ClientOperationEnumVd.UPDATE);
		
		bean.getPartnerCode().setRequired(true);
		bean.getStatus().setEditable(false);
		return bean;
	}

	public UserAccountBean initDeleteFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		
		bean.setAction(ClientOperationEnumVd.DELETE);
		
		bean.getPartnerCode().setRequired(true);
		return bean;
	}

	public UserAccountBean initChangeStatusFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findPartnerByCode(ctx, partnerCode);
		
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		
		bean.getPartnerCode().setRequired(true);
		bean.getStatus().setRequired(true);
		return bean;
	}

}
