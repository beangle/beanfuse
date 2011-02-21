//$Id: SingleWordStringPredicate.java,v 1.1 2006/10/12 14:40:28 chaostone Exp $
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
import org.beanfuse.lang.SeqStringUtil;

public class SingleWordStringPredicate implements Predicate {

	public boolean evaluate(final Object str) {
		return (str instanceof String)
				&& (((String) str).indexOf(SeqStringUtil.DELIMITER) == -1);
	}

}
