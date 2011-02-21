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

public class CollectionHasUpto1ElementPredicate implements Predicate {

	public boolean evaluate(final Object object) {
		boolean success = true;
		if (object instanceof Collection) {
			success = ((Collection) object).size() < 2;
		}
		return success;
	}

}
