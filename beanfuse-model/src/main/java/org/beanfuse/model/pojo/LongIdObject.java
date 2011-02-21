//$Id: LongIdEntity.java,v 1.1 2007-6-2 下午12:25:22 chaostone Exp $
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
 *chaostone      2007-6-2         Created
 *  
 ********************************************************************************/

package org.beanfuse.model.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.beanfuse.model.AbstractEntity;

/**
 * 有Long id支持的实体实现
 * 
 * @author chaostone
 * 
 */
public class LongIdObject extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public LongIdObject() {
		super();
	}

	public LongIdObject(final Long id) {
		super();
		this.id = id;
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
		if (!(object instanceof LongIdObject)) {
			return false;
		}
		LongIdObject rhs = (LongIdObject) object;
		if (null == getId() || null == rhs.getId()) {
			return false;
		}
		return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
	}

	public String key() {
		return "id";
	}

	public Serializable getEntityId() {
		return id;
	}

}
