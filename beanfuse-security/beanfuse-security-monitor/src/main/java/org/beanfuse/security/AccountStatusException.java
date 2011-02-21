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
 * chaostone             2006-2-17            Created
 *  
 ********************************************************************************/
package org.beanfuse.security;

public class AccountStatusException extends AuthenticationException implements
		AuthenticationBreakException {
	private static final long serialVersionUID = 5750259152188732891L;

	public AccountStatusException() {
		super(Authentication.ERROR_UNACTIVE);
	}

	public AccountStatusException(String message) {
		super(message);
	}

}
