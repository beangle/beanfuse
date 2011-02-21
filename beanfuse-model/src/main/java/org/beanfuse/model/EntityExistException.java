//$Id: EntityExistException.java,v 1.1 2006/10/12 14:39:05 chaostone Exp $
/*
 *
 * Copyright c 2005-2009.
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.  
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2005-9-15         Created
 *  
 ********************************************************************************/

package org.beanfuse.model;

/**
 * 新建Pojo时，插入冲突异常.
 * 
 * @author chaostone 2005-9-15
 */
public class EntityExistException extends RuntimeException {
	private static final long serialVersionUID = -1440611764784128828L;

	public EntityExistException(final String message) {
		super(message);
	}

	public EntityExistException(final String message, Throwable cause) {
		super(message, cause);
	}
}
