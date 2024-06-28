package com.agro360.ws.controller.core;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.operation.security.AuthenticatedUser;
import com.agro360.ws.controller.common.AbstractController;

import jakarta.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/")
public class IndexController extends AbstractController {
	
	@GetMapping()
	public ResponseEntity<ModelMap> indexAction(HttpServletRequest request, Authentication auth){
		var user = (AuthenticatedUser) auth.getPrincipal();
		
		var csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        var model = new ModelMap(RECORD_MODEL_KEY, user.getUserInfo());
        if( csrfToken != null ) {
        	csrfToken.getToken();
        }
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("get-profil")
	public ResponseEntity<ModelMap> getProfilAction(Authentication auth){
		var user = (AuthenticatedUser) auth.getPrincipal();
		var model = new ModelMap(RECORD_MODEL_KEY, user.getUserInfo());
		return ResponseEntity.ok(model);
	}
}
