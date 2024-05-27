package com.agro360.operation.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.agro360.bo.bean.core.UserAccountBean;

import lombok.Getter;

public class AuthenticatedUser extends User{

	private static final long serialVersionUID = 2427906991409676359L;
	
	public AuthenticatedUser(UserAccountBean userInfo, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(userInfo.getPartner().getPartnerCode().getValue(), userInfo.getPassword().getValue(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userInfo = userInfo;
	}

	public AuthenticatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.userInfo = null;
	}
	
	@Getter
	private final UserAccountBean userInfo;
	
}
