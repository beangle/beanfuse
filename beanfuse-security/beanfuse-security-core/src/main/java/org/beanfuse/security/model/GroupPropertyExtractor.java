//$Id: GroupPropertyExporter.java,v 1.1 2007-3-11 上午09:55:14 chaostone Exp $
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
 *chaostone      2007-3-11         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.model;

import org.beanfuse.transfer.exporter.DefaultPropertyExtractor;

public class GroupPropertyExtractor extends DefaultPropertyExtractor {

	public GroupPropertyExtractor() {
		super();
	}

	/**
	 * FIXME for dateRealm
	 */
	public Object getPropertyValue(Object target, String property) throws Exception {
		Group group = (Group) target;
		if ("users".equals(property)) {
			return UserPropertyExtractor.getUserNames(group.getUsers());
		} else if ("managers".equals(property)) {
			return UserPropertyExtractor.getUserNames(group.getManagers());
		} else if ("dataRealm.studentTypes".equals(property)) {
			return "";
		} else if ("dataRealm.departments".equals(property)) {
			return "";
		} else {
			return super.getPropertyValue(target, property);
		}
	}

}
