//$Id: Pattern.java 2008-6-9 下午09:35:11 chaostone Exp $
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
 * chaostone      2008-6-9  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.restriction.model;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.restriction.ParamGroup;

public class Pattern extends LongIdObject implements
		org.beanfuse.security.restriction.Pattern {

	private static final long serialVersionUID = 3491583230212588933L;

	private String description;

	private String content;

	private ParamGroup paramGroup;
	
	public String getContent() {
		return content;
	}

	public void setContent(String pattern) {
		this.content = pattern;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String discription) {
		this.description = discription;
	}

	public ParamGroup getParamGroup() {
		return paramGroup;
	}

	public void setParamGroup(ParamGroup paramGroup) {
		this.paramGroup = paramGroup;
	}
	
}
