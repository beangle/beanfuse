//$Id: PatternParam.java 2008-7-29 上午11:25:40 chaostone Exp $
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
package org.beanfuse.security.restriction;

import java.util.Set;

import org.beanfuse.model.LongIdEntity;

public interface Param extends LongIdEntity {

	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

	public String getDescription();

	public void setDescription(String description);

	public ParamEditor getEditor();

	public void setEditor(ParamEditor editor);

	public void setMultiValue(boolean multiValue);

	public boolean isMultiValue();

	public Set getParamGroups();

	public void setParamGroups(Set paramGroups);

}
