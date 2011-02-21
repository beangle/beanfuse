//$Id: EntityNotExistException.java,v 1.1 2006/10/12 14:39:05 chaostone Exp $
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
 * 删除Pojo时，没有对应的对象异常.
 * 
 * @author chaostone 2005-9-15
 */
public class EntityNotExistException extends RuntimeException {
	private static final long serialVersionUID = 3136311427259768845L;

	public EntityNotExistException(final String message) {
		super(message);
	}
}
