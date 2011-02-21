/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-5-13            Created
 *  
 ********************************************************************************/
package org.beanfuse.security.concurrent;

import org.beanfuse.security.AuthenticationBreakException;
import org.beanfuse.security.AuthenticationException;

public class ConcurrentLoginException extends AuthenticationException implements
		AuthenticationBreakException {
	private static final long serialVersionUID = -2827989849698493720L;

	String msg;

	public ConcurrentLoginException() {
		super();
	}

	public ConcurrentLoginException(String msg) {
		super();
		this.msg = msg;
	}

}
