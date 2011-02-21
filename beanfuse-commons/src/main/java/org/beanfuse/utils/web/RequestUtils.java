package org.beanfuse.utils.web;

import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {

	private RequestUtils() {
	}

	/**
	 * 获取远程访问的客户IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 查找当前调用的action对应的.do<br>
	 * 例如http://localhost/myapp/dd.do 返回dd.do<br>
	 * http://localhost/myapp/dir/to/dd.do 返回dir/to/dd.do
	 * 
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request) {
		String actionName = request.getServletPath();
		if (actionName.startsWith("/")) {
			actionName = actionName.substring(1);
		}
		return actionName;
	}

	public static String encodeAttachName(HttpServletRequest request, String attch_name)
			throws Exception {
		String agent = request.getHeader("USER-AGENT");
		String newName = null;
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			newName = URLEncoder.encode(attch_name, "UTF-8");
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
			newName = MimeUtility.encodeText(attch_name, "UTF-8", "B");
		}
		return newName;
	}

}
