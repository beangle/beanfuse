//$Id: UserCategoryServiceImpl.java 2008-8-3 下午07:14:41 chaostone Exp $
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
package org.beanfuse.security.service;

import java.util.List;

import org.beanfuse.security.UserCategory;
import org.beanfuse.security.service.UserCategoryService;
import org.beanfuse.persist.impl.BaseServiceImpl;

public class UserCategoryServiceImpl extends BaseServiceImpl implements UserCategoryService {

	public List getCategories() {
		return entityDao.loadAll(UserCategory.class);
	}

}
