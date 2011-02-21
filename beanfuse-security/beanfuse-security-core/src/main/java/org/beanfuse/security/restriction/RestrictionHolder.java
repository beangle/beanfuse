//$Id: RestrictionAuthority.java 2008-8-2 下午04:37:23 chaostone Exp $
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
 * chaostone      2008-8-2  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.restriction;

import java.util.Set;

import org.beanfuse.model.LongIdEntity;

public interface RestrictionHolder extends LongIdEntity {

	public Set getRestrictions();

	public void setRestrictions(Set restrictions);
}
