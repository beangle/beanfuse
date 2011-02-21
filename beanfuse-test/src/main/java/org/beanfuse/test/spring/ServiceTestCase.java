//$Id$
/*
 *
 * Copyright c 2005-2009.
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.  
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2005-9-5         Created
 *  
 ********************************************************************************/

package org.beanfuse.test.spring;

import org.beanfuse.persist.EntityService;

/**
 * 基础服务的测试用例
 * 
 * @author chaostone 2005-9-8
 */

public class ServiceTestCase extends SpringTestCase {

	public EntityService entityService;

	public EntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}


}
