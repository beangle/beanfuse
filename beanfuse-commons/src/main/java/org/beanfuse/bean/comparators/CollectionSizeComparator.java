/*
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
 * chaostone             2006-5-20            Created
 *  
 ********************************************************************************/
package org.beanfuse.bean.comparators;

import java.util.Collection;
import java.util.Comparator;

/**
 * 比较两个集合，元素多的大
 * 
 * @author chaostone
 * 
 */
public class CollectionSizeComparator implements Comparator {

	public int compare(final Object object1, final Object object2) {
		Collection first = (Collection) object1;
		Collection second = (Collection) object2;
		if (first.equals(second)) {
			return 0;
		} else {
			return first.size() - second.size();
		}
	}
}
