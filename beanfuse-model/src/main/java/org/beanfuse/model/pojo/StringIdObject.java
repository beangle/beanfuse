//$Id: CodeObject.java,v 1.1 2007-8-3 上午10:28:53 chaostone Exp $
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
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-8-3         Created
 *  
 ********************************************************************************/

package org.beanfuse.model.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.beanfuse.model.AbstractEntity;

public class StringIdObject extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	protected String id;

	public String key() {
		return "id";
	}

	public Serializable getEntityId() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(final String code) {
		this.id = code;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-64900959, -454788261).append(this.id)
				.toHashCode();
	}
	/**
	 * 比较id,如果任一方id是null,则不相等
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(final Object object) {
		if (!(object instanceof StringIdObject)) {
			return false;
		}
		StringIdObject rhs = (StringIdObject) object;
		if (null == getId() || null == rhs.getId()) {
			return false;
		}
		return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
	}

}
