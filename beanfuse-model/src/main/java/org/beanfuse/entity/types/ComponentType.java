//$Id: ComponentType.java May 3, 2008 3:21:16 PM chaostone Exp $
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.entity.Type;

public class ComponentType extends AbstractType {
	private Class componentClass;
	
	private final Map propertyTypes = new HashMap(0);

	public boolean isComponentType() {
		return true;
	}

	public String getName() {
		return componentClass.toString();
	}

	public Class getReturnedClass() {
		return componentClass;
	}

	public ComponentType() {
		super();
	}

	public ComponentType(Class componentClass) {
		super();
		this.componentClass = componentClass;
	}

	/**
	 * Get the type of a particular (named) property
	 */
	public Type getPropertyType(String propertyName) {
		Type type = (Type) propertyTypes.get(propertyName);
		if (null == type) {
			Method getMethod = MethodUtils.getAccessibleMethod(componentClass,
					"get" + StringUtils.capitalize(propertyName),
					(Class[]) null);
			if (null != getMethod) {
				return new IdentifierType(getMethod.getReturnType());
			}
		}
		return type;
	}

	public Map getPropertyTypes() {
		return propertyTypes;
	}

}
