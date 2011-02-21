package org.beanfuse.security;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * 登录失败异常
 * 
 * @author chaostone
 * 
 */
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -3529782031102169004L;

	public AuthenticationException() {
		super();
	}

	/**
	 * @param message
	 */
	public AuthenticationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AuthenticationException(Throwable cause) {
		super(ExceptionUtils.getFullStackTrace(cause));
	}

}
