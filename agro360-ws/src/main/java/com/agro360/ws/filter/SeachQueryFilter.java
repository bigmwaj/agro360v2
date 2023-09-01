package com.agro360.ws.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;

import com.agro360.ws.servlet.SeachQueryHttpServletRequestWrapper;

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
