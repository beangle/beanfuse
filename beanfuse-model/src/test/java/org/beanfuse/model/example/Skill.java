//$Id: Skill.java May 4, 2008 8:01:26 PM chaostone Exp $
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

public class Skill extends LongIdObject {

	String name;

	SkillType skillType;

	public SkillType getSkillType() {
		return skillType;
	}

	public void setSkillType(SkillType skillType) {
		this.skillType = skillType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
