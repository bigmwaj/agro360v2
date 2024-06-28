package com.agro360.operation.rule.core.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.dao.core.IUserAccountDao;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.common.AbstractRule;
import com.agro360.operation.rule.exception.RuleException;
import com.agro360.vd.core.UserAccountStatusEnumVd;

public class AccountTargetStatusIs extends AbstractRule<PartnerBean> {
	
	private final UserAccountStatusEnumVd status;
	
	@Autowired
	private IUserAccountDao userAccountDao;
	
	public AccountTargetStatusIs(@NonNull UserAccountStatusEnumVd status) {
		this.status = status;
	}
	
	@Override
	public boolean eval(ClientContext ctx, PartnerBean bean) {

		var partnerCode = bean.getPartnerCode().getValue();
		if( ! userAccountDao.existsById(partnerCode) ) {
			throw new RuleException(String.format("Le partenaire %s n'a pas de compte!", partnerCode));
		}
		
		var userAccountDto = userAccountDao.getReferenceById(partnerCode);
		
		switch (userAccountDto.getStatus()) {
		case DISABLED:
		case LOCKED:
			return UserAccountStatusEnumVd.ENABLED.equals(status);
		case ENABLED:
			return UserAccountStatusEnumVd.DISABLED.equals(status);
		}
		return false;
	}
}