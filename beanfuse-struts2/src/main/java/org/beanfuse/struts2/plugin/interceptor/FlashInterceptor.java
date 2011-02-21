package org.beanfuse.struts2.plugin.interceptor;

import org.beanfuse.struts2.route.Flash;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * ROR's flash
 * 
 * @author chaostone
 * 
 */
public class FlashInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = invocation.invoke();
		Flash flash = (Flash) invocation.getInvocationContext().getSession().get("flash");
		if (null != flash) {
			flash.nextToNow();
		}
		return result;
	}

}
