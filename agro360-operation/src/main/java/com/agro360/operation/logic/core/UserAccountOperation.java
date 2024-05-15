package com.agro360.operation.logic.core;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.bo.mapper.CoreMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dao.core.IUserAccountDao;
import com.agro360.dto.core.UserAccountDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class UserAccountOperation extends AbstractOperation<UserAccountDto, String> {

	@Autowired
	private IUserAccountDao dao;
	
	@Autowired
	private IPartnerDao partnerDao;

	@Override
	protected IDao<UserAccountDto, String> getDao() {
		return dao;
	}
	
	@RuleNamespace("core/user/create")
	public void createUserAccount(ClientContext ctx, UserAccountBean bean) {
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var dto = new UserAccountDto();
		dto.setPartnerCode(partnerCode);
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setPassword, bean.getPassword());
		setDtoValue(dto::setLang, bean.getLang());
		setDtoValue(dto::setMagasin, bean.getMagasin());
		setDtoValue(dto::setRoles, bean.getRoles());
		super.save(ctx, dto);		
	}
	
	@RuleNamespace("core/user/update")
	public void updateUserAccount(ClientContext ctx, UserAccountBean bean) {
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var dto = dao.getReferenceById(partnerCode);
		setDtoChangedValue(dto::setPassword, bean.getPassword());
		setDtoChangedValue(dto::setLang, bean.getLang());
		setDtoChangedValue(dto::setMagasin, bean.getMagasin());
		setDtoChangedValue(dto::setRoles, bean.getRoles());
		super.save(ctx, dto);
	}
	
	private void changeUserAccountStatus(ClientContext ctx, UserAccountBean bean) {
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var dto = dao.getReferenceById(partnerCode);
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		super.save(ctx, dto);		
	}
	
	@RuleNamespace("core/user/deactivate")
	public void deactivateUserAccount(ClientContext ctx, UserAccountBean bean) {
		changeUserAccountStatus(ctx, bean);	
	}
	
	@RuleNamespace("core/user/activate")
	public void activateUserAccount(ClientContext ctx, UserAccountBean bean) {
		changeUserAccountStatus(ctx, bean);	
	}
	
	@RuleNamespace("core/user/delete")
	public void deleteUserAccount(ClientContext ctx, UserAccountBean bean) {
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var dto = dao.getReferenceById(partnerCode);
		dao.delete(dto);
	}
	
	public UserAccountBean findUserAccountByCode(ClientContext ctx, String partnerCode) {
		var dto = dao.getReferenceById(partnerCode);
		var partnerDto = partnerDao.getReferenceById(partnerCode);
		return CoreMapper.map(dto, partnerDto);		
	}
//	
//	public AuthenticatedUser authenticate(ClientContext ctx, String username, String password) {
//		
//		var enabled = true;
//		var accountNonExpired = false;
//		var credentialsNonExpired = false;
//		var accountNonLocked = true;
//		var authorities = Collections.singleton(new AuthenticatedUserGrantedAuthority("USER"));
//		var authUser = new AuthenticatedUser(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//
//		return authUser;
//	}

	public Optional<UserAccountBean> findUserAccountByUserName(String username) {
		return dao.findOneByPartnerPhoneOrPartnerEmail(username).map(e -> CoreMapper.map(e, partnerDao.getReferenceById(e.getPartnerCode())));
	}

}
