//$Id: CollectionType.java May 3, 2008 3:21:45 PM chaostone Exp $
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

import java.lang.reflect.Array;

import org.beanfuse.entity.Type;

public class CollectionType extends AbstractType {

	public boolean isCollectionType() {
		return true;
	}

	private Class collectionClass;

	private Type elementType;

	private Class indexClass;

	private boolean array = false;;

	public String getName() {
		StringBuilder buffer = new StringBuilder();
		if (null != collectionClass) {
			buffer.append(collectionClass.getName());
		}
		buffer.append(':');
		if (null != indexClass) {
			buffer.append(indexClass.getName());
		}
		buffer.append(':');
		buffer.append(elementType.getName());
		return buffer.toString();
	}

	public Type getPropertyType(String property) {
		return elementType;
	}

	/**
	 * The collection element type
	 */
	public Type getElementType() {
		return elementType;
	}

	public void setElementType(Type elementType) {
		this.elementType = elementType;
	}

	/**
	 * The collection index type (or null if the collection has no index)
	 */
	public Class getIndexClass() {
		return indexClass;
	}

	/**
	 * Is this collection indexed?
	 */
	public boolean hasIndex() {
		return (null != indexClass) && (indexClass.equals(int.class));
	}

	public Class getReturnedClass() {
		return collectionClass;
	}

	public Class getCollectionClass() {
		return collectionClass;
	}

	public void setCollectionClass(Class collectionClass) {
		this.collectionClass = collectionClass;
	}

	/**
	 * Is the collection an array?
	 */
	public boolean isArray() {
		return array;
	}

	public void setArray(boolean isArray) {
		this.array = isArray;
	}

	public Object newInstance() {
		if (array) {
			return Array.newInstance(elementType.getReturnedClass(), 0);
		} else {
			return super.newInstance();
		}
	}
	/**
	 * Is the collection a primitive array?
	 */
	// public boolean isPrimitiveArray();
}
