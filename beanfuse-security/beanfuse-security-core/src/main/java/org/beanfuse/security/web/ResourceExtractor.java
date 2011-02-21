//$Id: FunctionPointExtractor.java,v 1.1 2007-7-22 下午04:21:35 chaostone Exp $
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
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-7-22         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.web;

import javax.servlet.http.HttpServletRequest;

/**
 * 从request中提取访问系统的功能点名称。
 * 
 * @author chaostone
 * 
 */
public interface ResourceExtractor {

	public String extract(HttpServletRequest request);
	
	public String extract(String URI);
}
