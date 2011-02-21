//$Id: ValidEntityPredicate.java,v 1.1 2006/10/12 14:40:28 chaostone Exp $
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
 * chaostone             2005-11-11         Created
 *  
 ********************************************************************************/

package org.beanfuse.model.predicates;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;

import org.beanfuse.model.Entity;

/**
 * 有效实体判断谓词
 * 
 * @author chaostone
 * 
 */
public class ValidEntityPredicate implements Predicate {

	/**
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(final Object value) {
		if (null == value) {
			return false;
		}
		try {
			Serializable key = (Serializable) PropertyUtils.getProperty(value,
					((Entity) value).key());
			return ValidEntityKeyPredicate.getInstance().evaluate(key);
		} catch (Exception e) {
			return false;
		}
	}

	public static final ValidEntityPredicate INSTANCE = new ValidEntityPredicate();

	public static ValidEntityPredicate getInstance() {
		return INSTANCE;
	}
}
