package com.jaw.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationErrorFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest request,
							HttpServletResponse response,
							FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}
