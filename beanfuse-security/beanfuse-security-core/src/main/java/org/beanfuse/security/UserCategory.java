//$Id: UserCategory.java 2008-8-3 下午07:11:23 chaostone Exp $
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
package org.beanfuse.security;

import org.beanfuse.model.LongIdEntity;

/**
 * 用户类别
 * 
 * @author chaostone
 * 
 */
public interface UserCategory extends LongIdEntity {

	public String getName();

	public void setName(String name);

}
