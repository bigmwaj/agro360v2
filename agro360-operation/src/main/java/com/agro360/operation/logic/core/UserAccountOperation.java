package com.agro360.operation.logic.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.bo.mapper.CoreMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.IUserAccountDao;
import com.agro360.dto.core.UserAccountDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class UserAccountOperation extends AbstractOperation<UserAccountDto, String> {

	@Autowired
	private IUserAccountDao dao;

	@Override
	protected IDao<UserAccountDto, String> getDao() {
		return dao;
	}
	
	@RuleNamespace("core/user/create")
	public void createPartner(ClientContext ctx, UserAccountBean bean) {
		var dto = new UserAccountDto();
		setDtoValue(dto::setPartnerCode, bean.getPartnerCode());
		setDtoValue(dto::setStatus, bean.getStatus());
		super.save(dto);		
	}
	
	@RuleNamespace("core/user/update")
	public void updatePartner(ClientContext ctx, UserAccountBean bean) {
		var dto = dao.getReferenceById(bean.getPartnerCode().getValue());
		setDtoChangedValue(dto::setPassword, bean.getPassword());
		super.save(dto);
	}
	
	private void changePartnerStatus(ClientContext ctx, UserAccountBean bean) {
		var dto = dao.getReferenceById(bean.getPartnerCode().getValue());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		super.save(dto);		
	}
	
	@RuleNamespace("core/partner/deactivate")
	public void deactivatePartner(ClientContext ctx, UserAccountBean bean) {
		changePartnerStatus(ctx, bean);	
	}
	
	@RuleNamespace("core/partner/activate")
	public void activatePartner(ClientContext ctx, UserAccountBean bean) {
		changePartnerStatus(ctx, bean);	
	}
	
	@RuleNamespace("core/partner/delete")
	public void deletePartner(ClientContext ctx, UserAccountBean bean) {
		var dto = dao.getReferenceById(bean.getPartnerCode().getValue());
		dao.delete(dto);
	}
	
	public UserAccountBean findPartnerByCode(ClientContext ctx, String partnerCode) {
		var dto = dao.getReferenceById(partnerCode);
		return CoreMapper.map(dto);		
	}
	

}
