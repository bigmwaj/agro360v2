package com.agro360.service.core;

import java.util.Collections;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.UserAccountOperation;
import com.agro360.operation.security.AuthenticatedUser;
import com.agro360.operation.security.AuthenticatedUserGrantedAuthority;
import com.agro360.service.common.AbstractService;
import com.agro360.service.common.ServiceValidationException;
import com.agro360.vd.core.UserAccountStatusEnumVd;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserAccountService extends AbstractService implements UserDetailsService {

	@Autowired
	private UserAccountOperation operation;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var msgTpl = "Aucun utilisateur trouvé avant pour nom d'utilisateur %s";
		Supplier<UsernameNotFoundException> ex;
		ex = () -> new UsernameNotFoundException(String.format(msgTpl, username));
		var user =  operation.findUserAccountByUserName(username).orElseThrow(ex);
		
		var status = user.getStatus().getValue();
//		var passwordLastChangeDate = user.getPasswordLastChangeDate().getValue();
		
//		LocalDateTime.now().minus(passwordLastChangeDate.);
		
		boolean enabled = UserAccountStatusEnumVd.ENABLED.equals(status);
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;		
		var authorities = Collections.singleton(new AuthenticatedUserGrantedAuthority("USER"));
		return new AuthenticatedUser(user, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public void save(ClientContext ctx, UserAccountBean bean) throws ServiceValidationException{
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		switch (bean.getAction()) {
		case CREATE:
			operation.createUserAccount(ctx, bean);
			ctx.success(String.format("Le compte du partenaire %s a été créé avec succès!", partnerCode));
			break;
			
		case UPDATE:
			ctx.success(String.format("Le compte du partenaire %s a été modifié avec succès!", partnerCode));
			operation.updateUserAccount(ctx, bean);
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ENABLED:
				ctx.success(String.format("Le compte du partenaire %s a été activé avec succès!", partnerCode));
				operation.activateUserAccount(ctx, bean);
				
				break;
			case DISABLED:
				ctx.success(String.format("Le compte du partenaire %s a été désactivé avec succès!", partnerCode));
				operation.deactivateUserAccount(ctx, bean);
				
				break;

			default:
				break;
			}
			break;
			
		default:
			
		}
	}

	public UserAccountBean findUserAccount(ClientContext ctx, String value) {
		return operation.findUserAccountByCode(ctx, value);
	}
}
