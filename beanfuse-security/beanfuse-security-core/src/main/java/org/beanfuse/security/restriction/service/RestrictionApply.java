//$Id: RestrictionApply.java 2008-6-9 下午09:27:42 chaostone Exp $
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
package org.beanfuse.security.restriction.service;

import java.util.Collection;

import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.restriction.Restriction;
import org.beanfuse.security.restriction.Pattern;

/**
 * 限制应用服务
 * 
 * @author chaostone
 * 
 */
public interface RestrictionApply {

	public void apply(EntityQuery query, Collection patterns, Collection restrictions);

	public void apply(EntityQuery query, Pattern pattern, Restriction restriction);
}
