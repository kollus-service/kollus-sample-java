package com.kollus.kr.kollus_sample_java.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DefaultFilter implements Filter {

	private RequestDispatcher defaultRequestDispatcher;

	
	public void destroy() {
	}

	
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		this.defaultRequestDispatcher.forward(arg0, arg1);

	}

	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.defaultRequestDispatcher = filterConfig.getServletContext().getNamedDispatcher("default");
	}

}
