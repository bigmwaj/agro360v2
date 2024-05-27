package com.agro360.operation.security;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class AuthenticatedUserGrantedAuthority implements GrantedAuthority{

	private static final long serialVersionUID = -497870597905063392L;
	
	private final String authority;
}
