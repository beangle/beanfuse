//$Id: SkillType.java May 4, 2008 8:52:38 PM chaostone Exp $
/*
 *
 * Copyright c 2005-2009.
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      May 4, 2008  Created
 *  
 ********************************************************************************/
package org.beanfuse.model.example;

import org.beanfuse.model.pojo.LongIdObject;

public class SkillType extends LongIdObject {

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
