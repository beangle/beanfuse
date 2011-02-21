//$Id: NotEmptyPropertySelector.java,v 1.1 2006/10/16 00:41:47 chaostone Exp $
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
 * chaostone             2005-9-19         Created
 *  
 ********************************************************************************/

package org.beanfuse.persist.hibernate;

import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.Type;

public class NotEmptyPropertySelector implements PropertySelector {

	private static final long serialVersionUID = 2265767236729495415L;

	/**
	 * @see org.hibernate.criterion.Example.PropertySelector#include(java.lang.Object,
	 *      java.lang.String, org.hibernate.type.Type)
	 */
	public boolean include(Object object, String propertyName, Type type) {
		if (object == null) {
			return false;
		}
		if ((object instanceof Number) && ((Number) object).longValue() == 0) {
			return false;
		}
		if ("".equals(object)) {
			return false;
		}
		return true;
	}

}
