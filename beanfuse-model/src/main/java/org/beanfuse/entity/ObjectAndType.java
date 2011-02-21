//$Id:ObjAndType.java 2009-1-11 上午01:56:40 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.entity;

/**
 * 对象和类型
 * @author chaostone
 *
 */
public class ObjectAndType {

	private Object obj;
	private Type type;

	public ObjectAndType(Object obj, Type type) {
		super();
		this.obj = obj;
		this.type = type;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
