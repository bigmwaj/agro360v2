package com.agro360.ws.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.agro360.service.core.UserAccountService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class AppAuthenticationRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserAccountService authMngr;

	@Autowired
	private AppAuthenticationTokenUtil jwtTokenUtil;
	
//	private Optional<String> getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AppAuthenticationTokenUtil.AUTHORIZATION);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AppAuthenticationTokenUtil.BEARER)) {
//            return Optional.of(bearerToken.substring(7));
//        }
//        return Optional.empty();
//    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
//		final Optional<String> jwt = getJwtFromRequest(request);
//		jwt.ifPresent(token -> {
//			if (jwtTokenUtil.validateToken(token)) {
//                setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), token);
//            }
//		});
		
		final String requestTokenHeader = request.getHeader(AppAuthenticationTokenUtil.AUTHORIZATION);

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith(AppAuthenticationTokenUtil.BEARER)) {
			try {
				jwtToken = requestTokenHeader.substring(AppAuthenticationTokenUtil.BEARER.length());
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String! ");
			chain.doFilter(request, response);
			return;
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = authMngr.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), jwtToken);
			}
		}
		chain.doFilter(request, response);
	}

	private void setSecurityContext(WebAuthenticationDetails authDetails, String token) {
        final String username = jwtTokenUtil.getUsernameFromToken(token);
        final List<GrantedAuthority> roles = jwtTokenUtil.getRoles(token)
        		.stream()
        		.map(SimpleGrantedAuthority::new)
        		.collect(Collectors.toList());
        final UserDetails userDetails = new User(username, "", roles);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authentication.setDetails(authDetails);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
