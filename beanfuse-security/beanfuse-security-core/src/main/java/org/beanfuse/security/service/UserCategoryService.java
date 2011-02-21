//$Id: UserCategoryService.java 2008-8-3 下午07:13:43 chaostone Exp $
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

public interface UserCategoryService {

	/**
	 * 查询所有类别
	 * 
	 * @return
	 */
	public List getCategories();
}
