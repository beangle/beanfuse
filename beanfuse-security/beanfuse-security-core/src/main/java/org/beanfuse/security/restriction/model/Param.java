//$Id: PatternParam.java 2008-7-29 上午11:27:30 chaostone Exp $
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
 * chaostone      2008-7-29  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.restriction.model;

import java.util.HashSet;
import java.util.Set;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.restriction.ParamEditor;

public class Param extends LongIdObject implements org.beanfuse.security.restriction.Param {
	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	private String type;

	private boolean multiValue;

	private ParamEditor editor;

	private Set paramGroups = new HashSet();

	public boolean isMultiValue() {
		return multiValue;
	}

	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ParamEditor getEditor() {
		return editor;
	}

	public void setEditor(ParamEditor editor) {
		this.editor = editor;
	}

	public Set getParamGroups() {
		return paramGroups;
	}

	public void setParamGroups(Set paramGroups) {
		this.paramGroups = paramGroups;
	}

}
