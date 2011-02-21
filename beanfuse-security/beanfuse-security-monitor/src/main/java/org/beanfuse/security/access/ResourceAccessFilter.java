package org.beanfuse.security.access;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.beanfuse.security.access.config.ConfigLoader;

/**
 * 资源访问过滤器
 * 
 * @author chaostone
 * 
 */
public class ResourceAccessFilter implements Filter {

	private ResourceAccessor accessor;
	public static final String SESSION_ATTRIBUTE_KEY = "ResourceAccessor";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hr = (HttpServletRequest) request;
		AccessLog log = accessor.beginAccess(hr, System.currentTimeMillis());
		try {
			chain.doFilter(request, response);
		} finally {
			accessor.endAccess(log, System.currentTimeMillis());
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		accessor = ConfigLoader.getInstance().getConfig().getAccessor();
		filterConfig.getServletContext().setAttribute(SESSION_ATTRIBUTE_KEY, accessor);
		accessor.start();
	}

	public void destroy() {
		accessor.finish();
		accessor = null;
	}
}