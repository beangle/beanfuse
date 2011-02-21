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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2005-9-8         Created
 *  
 ********************************************************************************/

package org.beanfuse.test.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * 加载spring的应用配置作为测试的环境
 * 
 * @author chaostone 2005-9-8
 */
public class SpringTestCase extends AbstractDependencyInjectionSpringContextTests {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public SpringTestCase() {
		this.setName(getClass().getName());
	}

	protected void prepareTestInstance() throws Exception {
		setDependencyCheck(true);
		setAutowireMode(AUTOWIRE_BY_NAME);
	}

	protected String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext.xml" };
	}

}
