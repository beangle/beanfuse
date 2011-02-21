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
 * chaostone             2006-12-7            Created
 *  
 ********************************************************************************/
package org.beanfuse.model.predicates;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.Predicate;

import org.beanfuse.model.Entity;

public class IdNotInPredicate implements Predicate {

	private final Set idSet;

	public IdNotInPredicate(Collection ids) {
		idSet = new HashSet(ids);
	}

	public boolean evaluate(Object arg0) {
		Entity entity = (Entity) arg0;
		return !idSet.contains(entity.getEntityId());
	}
}
