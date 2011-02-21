//$Id: Pattern.java 2008-6-9 下午09:22:25 chaostone Exp $
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
package org.beanfuse.security.restriction;

import org.beanfuse.model.LongIdEntity;

/**
 * 限制模式
 * 
 * @author chaostone
 * 
 */
public interface Pattern extends LongIdEntity {

	public String getContent();

	public void setContent(String content);

	public String getDescription();

	public void setDescription(String description);

	public ParamGroup getParamGroup();

	public void setParamGroup(ParamGroup paramGroup);

}
