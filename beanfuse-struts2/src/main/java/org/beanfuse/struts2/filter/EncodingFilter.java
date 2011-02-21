package org.beanfuse.struts2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

	protected String encoding = "utf-8";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	public void destroy() {
		encoding = null;
	}

}