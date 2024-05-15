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

	public UserAccountBean initLoginFormBean(ClientContext ctx) {
		var bean = new UserAccountBean();
		return bean;
	}
	
	public UserAccountBean initCreateFormBean(ClientContext ctx) {
		var bean = new UserAccountBean();
		
		bean.setAction(ClientOperationEnumVd.CREATE);
		
		bean.getStatus().setValue(UserAccountStatusEnumVd.ENABLED);
		return bean;
	}
	
	public UserAccountBean initEditFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findUserAccountByCode(ctx, partnerCode);
		bean.setAction(ClientOperationEnumVd.UPDATE);
		
		return bean;
	}

	public UserAccountBean initDeleteFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findUserAccountByCode(ctx, partnerCode);
		
		bean.setAction(ClientOperationEnumVd.DELETE);
		return bean;
	}

	public UserAccountBean initChangeStatusFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findUserAccountByCode(ctx, partnerCode);
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		return bean;
	}

}
