//$Id: UserPropertyExporter.java,v 1.1 2007-2-7 下午08:00:39 chaostone Exp $
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
 * ============         ============        ============
 *chaostone      2007-2-7         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.model;

import java.util.Collection;
import java.util.Iterator;

import org.beanfuse.transfer.exporter.DefaultPropertyExtractor;

public class UserPropertyExtractor extends DefaultPropertyExtractor {

	public Object getPropertyValue(Object target, String property) throws Exception {
		User user = (User) target;
		if("id".equals(property)){
			return new Long(121212);
		}
		if ("groups".equals(property)) {
			return getPropertyIn(user.getGroups(), "name");
		} else if ("mngGroups".equals(property)) {
			return getPropertyIn(user.getMngGroups(), "name");
		} else if ("mngUsers".equals(property)) {
			return getUserNames(user.getMngUsers());
		} else {
			return super.getPropertyValue(target, property);
		}
	}

	public static final StringBuilder getUserNames(Collection users) {
		StringBuilder sb = new StringBuilder();
		for (Iterator iter = users.iterator(); iter.hasNext();) {
			User user = (User) iter.next();
			sb.append(user.getFullname()).append('(').append(user.getName()).append(')');
			if (iter.hasNext()) {
				sb.append(' ');
			}
		}
		return sb;
	}
}
