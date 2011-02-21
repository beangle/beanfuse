//$Id: IdentifierType.java May 3, 2008 3:56:50 PM chaostone Exp $
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
package org.beanfuse.entity.types;

public class IdentifierType extends AbstractType {

	private Class clazz;

	public IdentifierType() {
		super();
	}

	public IdentifierType(Class clazz) {
		super();
		this.clazz = clazz;
	}

	public String getName() {
		return clazz.toString();
	}

	public Class getReturnedClass() {
		return clazz;
	}

	public boolean isCollectionType() {
		return false;
	}

	public boolean isComponentType() {
		return false;
	}

	public boolean isEntityType() {
		return false;
	}

}
