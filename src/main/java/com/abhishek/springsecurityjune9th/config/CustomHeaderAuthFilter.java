package com.abhishek.springsecurityjune9th.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class CustomHeaderAuthFilter 
//extends GenericFilterBean
{

//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		
//		
//		String authHeader = httpRequest.getHeader("AXESS-HEADER");
//		/**
//		 * if the header is missing, access is denied.
//		 */
//		if(!StringUtils.hasText(authHeader)) {
//			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//		} else {
//			chain.doFilter(request, response);
//		}
//		
//		
//	}

}
