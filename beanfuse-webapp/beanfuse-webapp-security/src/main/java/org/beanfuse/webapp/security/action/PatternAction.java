//$Id: PatternAction.java 2008-8-6 上午09:33:34 chaostone Exp $
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
 * chaostone      2008-8-6  Created
 *  
 ********************************************************************************/
package org.beanfuse.webapp.security.action;

import org.beanfuse.model.Entity;
import org.beanfuse.security.restriction.ParamGroup;

public class PatternAction extends SecurityAction {

	protected void editSetting(Entity entity) {
		put("paramGroups", entityService.loadAll(ParamGroup.class));
	}

}
