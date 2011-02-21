//$Id: MenuItem.java 2008-7-28 下午04:29:29 chaostone Exp $
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
 * chaostone      2008-7-28  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.menu;

import org.beanfuse.model.LongIdEntity;
import org.beanfuse.security.Group;

public interface MenuAuthority extends LongIdEntity {

	public Group getGroup();

	public void setGroup(Group group);

	public Menu getMenu();

	public void setMenu(Menu menu);
}
