//$Id: Name.java May 3, 2008 10:33:10 PM chaostone Exp $
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
 * chaostone      May 3, 2008  Created
 *  
 ********************************************************************************/
package org.beanfuse.model.example;

import java.util.List;

import org.beanfuse.model.Component;

public class Name implements Component {
	String firstName;

	String lastName;

	List oldNames;

	public List getOldNames() {
		return oldNames;
	}

	public void setOldNames(List oldNames) {
		this.oldNames = oldNames;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
