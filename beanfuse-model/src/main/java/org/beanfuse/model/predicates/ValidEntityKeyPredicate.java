//$Id: ValidEntityKeyPredicate.java,v 1.1 2006/10/12 14:40:28 chaostone Exp $
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

import org.apache.commons.collections.Predicate;

import org.beanfuse.collection.predicates.NotEmptyStringPredicate;
import org.beanfuse.collection.predicates.NotZeroNumberPredicate;

/**
 * 判断实体类中的主键是否是有效主键
 * 
 * @author chaostone
 * 
 */
public class ValidEntityKeyPredicate implements Predicate {

	/**
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(Object value) {
		return NotEmptyStringPredicate.INSTANCE.evaluate(value)
				|| NotZeroNumberPredicate.INSTANCE.evaluate(value);
	}

	public static final ValidEntityKeyPredicate INSTANCE = new ValidEntityKeyPredicate();

	public static ValidEntityKeyPredicate getInstance() {
		return INSTANCE;
	}

}
