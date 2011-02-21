//$Id: NotZeroNumberPredicate.java,v 1.1 2006/10/12 14:40:28 chaostone Exp $
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

public class NotZeroNumberPredicate implements Predicate {

	/**
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(final Object value) {
		return (value instanceof Number) && (0 != ((Number) value).intValue());
	}

	public static final NotZeroNumberPredicate INSTANCE = new NotZeroNumberPredicate();

	public static NotZeroNumberPredicate getInstance() {
		return INSTANCE;
	}
}
