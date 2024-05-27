package com.agro360.ws.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import com.agro360.service.core.UserAccountService;

import jakarta.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	@Autowired
	private UserAccountService authMngr;

	@Value("${front.url}")
	private String frontUrl;

//	@Primary
//	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		var encoder = passwordEncoder();
		auth.userDetailsService(authMngr).passwordEncoder(encoder);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		var requestHandler = new CsrfTokenRequestAttributeHandler();
		// set the name of the attribute the CsrfToken will be populated on
		requestHandler.setCsrfRequestAttributeName("X-XSRF-Token");

		http.httpBasic(Customizer.withDefaults())
			.cors(e -> e.configurationSource(t -> corsConfig(t)))
			.csrf(e -> e.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					.csrfTokenRequestHandler(requestHandler).disable())
			.logout(this::logoutConfig)
			.formLogin(e -> e.loginProcessingUrl("/login"))
			.authorizeHttpRequests(e -> e.requestMatchers("/logout", "/login", "/resources/**").permitAll()
					.anyRequest().authenticated())
			.sessionManagement(e -> e.sessionCreationPolicy(SessionCreationPolicy.NEVER));
		return http.build();
	}
	
	private void logoutConfig(LogoutConfigurer<HttpSecurity> logout) {
		logout.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.permitAll();
	}

	@Bean
	public CorsConfiguration corsConfig(HttpServletRequest request) {
		var config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedOrigins(Collections.singletonList(frontUrl));
		config.setMaxAge(3600L);

		return config;
	}
}
