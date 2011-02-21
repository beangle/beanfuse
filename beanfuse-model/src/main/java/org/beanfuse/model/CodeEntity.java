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

package org.beanfuse.model;

public interface CodeEntity extends Entity {

	public String getCode();

	public void setCode(String code);
}
