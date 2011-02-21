//$Id: Entity.java,v 1.2 2006/12/07 10:51:10 chaostone Exp $
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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2005-10-29         Created
 *  
 ********************************************************************************/
package org.beanfuse.model;

import java.io.Serializable;

import org.beanfuse.model.predicates.ValidEntityPredicate;

public abstract class AbstractEntity implements Entity, Serializable {

	public boolean isSaved() {
		return isPO();
	}

	public boolean isPO() {
		return ValidEntityPredicate.getInstance().evaluate(this);
	}

	public boolean isVO() {
		return !isPO();
	}

}
