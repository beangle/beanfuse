//$Id: UserCategory.java 2008-8-3 下午07:11:57 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      2008-8-3  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.model;

import org.beanfuse.model.pojo.LongIdObject;

public class UserCategory extends LongIdObject implements org.beanfuse.security.UserCategory {

	private static final long serialVersionUID = -5929038500510261629L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserCategory() {
		super();
	}

	public UserCategory(Long id) {
		super();
		this.setId(id);
	}

}
