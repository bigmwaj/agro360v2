package com.agro360.operation.logic.core;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.agro360.vd.core.UserRoleEnumVd;

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
	
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@RuleNamespace("core/user/create")
	public void createUserAccount(ClientContext ctx, UserAccountBean bean) {
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var dto = new UserAccountDto();
		var roles  = bean.getRoles().getValue().stream().map(UserRoleEnumVd::name)
				.reduce((a, b) -> String.format("%s,%s",a, b)).orElse(null);
		dto.setPassword(passwordEncoder().encode(bean.getPassword().getValue()));
		dto.setPartnerCode(partnerCode);
		dto.setPasswordLastChangeDate(LocalDateTime.now());
		dto.setRoles(roles);
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setLang, bean.getLang());
		setDtoValue(dto::setMagasin, bean.getMagasin());
		super.save(ctx, dto);		
	}
	
	@RuleNamespace("core/user/update")
	public void updateUserAccount(ClientContext ctx, UserAccountBean bean) {
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var dto = dao.getReferenceById(partnerCode);
		var roles  = bean.getRoles().getValue().stream().map(UserRoleEnumVd::name)
				.reduce((a, b) -> String.format("%s,%s",a, b)).orElse(null);
		dto.setRoles(roles);
		setDtoChangedValue(dto::setLang, bean.getLang());
		setDtoChangedValue(dto::setMagasin, bean.getMagasin());
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
