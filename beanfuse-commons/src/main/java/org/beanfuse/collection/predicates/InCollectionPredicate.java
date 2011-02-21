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
 * chaostone             2006-5-27            Created
 *  
 ********************************************************************************/
package org.beanfuse.collection.predicates;

import java.util.Collection;

import org.apache.commons.collections.Predicate;

public class InCollectionPredicate implements Predicate {

	private final Collection objs;

	public InCollectionPredicate(Collection objs) {
		super();
		this.objs = objs;
	}

	public boolean evaluate(Object arg0) {
		if (arg0 instanceof Collection) {
			return objs.containsAll((Collection) arg0);
		} else {
			return objs.contains(arg0);
		}
	}

}
