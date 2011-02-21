//$Id: NotEmptyStringPredicate.java,v 1.1 2006/10/12 14:40:28 chaostone Exp $
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

package org.beanfuse.collection.predicates;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

public class NotEmptyStringPredicate implements Predicate {
	public static final NotEmptyStringPredicate INSTANCE = new NotEmptyStringPredicate();

	/**
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(final Object value) {
		return (null != value) && (value instanceof String)
				&& StringUtils.isNotEmpty((String) value);
	}

}
