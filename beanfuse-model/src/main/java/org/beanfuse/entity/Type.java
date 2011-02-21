//$Id: Type.java May 3, 2008 3:16:24 PM chaostone Exp $
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
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      May 3, 2008  Created
 *  
 ********************************************************************************/
package org.beanfuse.entity;

public interface Type {
	/**
	 * Is this type a collection type.
	 */
	public boolean isCollectionType();

	/**
	 * Is this type a component type. If so, the implementation must be castable
	 * to <tt>AbstractComponentType</tt>. A component type may own collections
	 * or associations and hence must provide certain extra functionality.
	 * 
	 * @see AbstractComponentType
	 * @return boolean
	 */
	public boolean isComponentType();

	/**
	 * Is this type an entity type?
	 * 
	 * @return boolean
	 */
	public boolean isEntityType();

	public Type getPropertyType(String property);

	public String getName();

	public Class getReturnedClass();

	public Object newInstance();
}
