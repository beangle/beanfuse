//$Id: MenuProfileAction.java,v 1.1 2008-8-7 上午09:36:29 鄂州蚊子 Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author 鄂州蚊子
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * 鄂州蚊子                 2008-8-7             Created
 *  
 ********************************************************************************/
package org.beanfuse.webapp.security.action;

import org.beanfuse.model.Entity;
import org.beanfuse.security.UserCategory;

public class MenuProfileAction extends SecurityAction {

	protected void editSetting(Entity entity) {
		put("categories", entityService.loadAll(UserCategory.class));
	}
}
