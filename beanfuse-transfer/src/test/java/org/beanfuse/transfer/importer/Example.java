//$Id: Example.java,v 1.1 2007-3-17 下午12:32:48 chaostone Exp $
/*
 * Copyright c 2005-2009
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
 * ============         ============        ============
 *chaostone      2007-3-17         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.importer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.beanfuse.model.pojo.LongIdObject;

public class Example extends LongIdObject {

	private static final long serialVersionUID = 1467632544620523331L;

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("id", this.getId())
				.append("name", this.name).toString();
	}
}
