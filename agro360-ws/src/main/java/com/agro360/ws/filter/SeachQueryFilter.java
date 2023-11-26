package com.agro360.ws.filter;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;

import com.agro360.ws.servlet.SeachQueryHttpServletRequestWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SeachQueryFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, 
			ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {
				
		var query = request.getParameter("q");

		if( query != null ) {
			var wrapper = new SeachQueryHttpServletRequestWrapper((HttpServletRequest) request);
			filterchain.doFilter(wrapper, response);
		}else {
			filterchain.doFilter(request, response);
		}
	}
}
