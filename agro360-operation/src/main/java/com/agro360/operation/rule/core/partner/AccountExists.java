package com.agro360.operation.rule.core.partner;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.dao.core.IUserAccountDao;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;

public class AccountExists extends AbstractRule<PartnerBean> {
	
	@Autowired
	private IUserAccountDao userAccountDao;
	
	@Override
	public boolean eval(ClientContext ctx, PartnerBean bean) {
		var partnerCode = bean.getPartnerCode().getValue();
		return userAccountDao.existsById(partnerCode);
	}
}