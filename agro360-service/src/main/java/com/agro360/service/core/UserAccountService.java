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
import com.agro360.operation.utils.AuthenticatedUser;
import com.agro360.operation.utils.AuthenticatedUserGrantedAuthority;
import com.agro360.service.common.AbstractService;

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
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;		
		var authorities = Collections.singleton(new AuthenticatedUserGrantedAuthority("USER"));
		return new AuthenticatedUser(user, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public void save(ClientContext ctx, UserAccountBean bean) {
		
		switch (bean.getAction()) {
		case CREATE:
			operation.createUserAccount(ctx, bean);
			break;
			
		case UPDATE:
			operation.updateUserAccount(ctx, bean);
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ENABLED:
				operation.activateUserAccount(ctx, bean);
				
				break;
			case DISABLED:
				operation.deactivateUserAccount(ctx, bean);
				
				break;

			default:
				break;
			}
			break;

		case DELETE:
			operation.deleteUserAccount(ctx, bean);
			break;
			
		default:
			
		}
	}

	public UserAccountBean findUserAccount(ClientContext ctx, String value) {
		return operation.findUserAccountByCode(ctx, value);
	}
}
