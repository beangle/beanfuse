//$Id: Entity.java,v 1.1 2007-2-9 下午09:41:09 chaostone Exp $
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
 *chaostone      2007-2-9         Created
 *  
 ********************************************************************************/

package org.beanfuse.model;

import java.io.Serializable;

/**
 * 实体类接口
 * 
 * @author chaostone
 * 
 */
public interface Entity extends Serializable {

	/**
	 * 主键的属性名
	 * 
	 * @return
	 */
	public String key();

	/**
	 * 是否为值对象
	 * 
	 * @return
	 */
	public boolean isVO();

	/**
	 * 是否是持久化对象
	 * 
	 * @return
	 */
	public boolean isPO();

	/**
	 * 返回实体的id
	 * 
	 * @return
	 */
	public Serializable getEntityId();
}
