//$Id: DefaultFunctionPointExtractor.java,v 1.1 2007-7-22 下午04:25:22 chaostone Exp $
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

public class SimpleResourceExtractor implements ResourceExtractor {

	public String extract(HttpServletRequest request) {
		return extract(request.getServletPath());
	}

	public String extract(String uri) {
		int firstSlot = uri.indexOf('/');
		firstSlot++;
		int lastDot = uri.lastIndexOf('?');
		if (lastDot < 0) {
			lastDot = uri.length();
		}
		return uri.substring(firstSlot, lastDot);
	}

}
