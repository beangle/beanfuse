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
 * chaostone             2008-4-29            Created
 *  
 ********************************************************************************/
package org.beanfuse.collection.page;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

public class EmptyPage extends AbstractList implements Page {

	public int getFirstPageNo() {
		return 0;
	}

	public int getMaxPageNo() {
		return 0;
	}

	public int getNextPageNo() {
		return 0;
	}

	public int getPageNo() {
		return 0;
	}

	public int getPageSize() {
		return 0;
	}

	public int getPreviousPageNo() {
		return 0;
	}

	public int getTotal() {
		return 0;
	}

	public boolean hasNext() {
		return false;
	}

	public boolean hasPrevious() {
		return false;
	}

	public Page next() {
		return this;
	}

	public Page previous() {
		return this;
	}

	public Object get(int index) {
		return null;
	}

	public int size() {
		return 0;
	}

	public Page moveTo(int pageNo) {
		return this;
	}

	public List getItems() {
		return Collections.EMPTY_LIST;
	}

}
