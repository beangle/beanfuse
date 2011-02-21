//$Id: DefaultItemImpoter.java,v 1.1 2007-7-9 下午02:48:47 chaostone Exp $
/*
 * Copyright c 2005-2009
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
 * chenweixiong              2007-7-9         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.importer;

public class DefaultItemImpoter extends ItemImporter {

	private Object current;

	public void beforeImportItem() {
	}

	public void setCurrent(Object object) {
		this.current = object;
	}

	public Object getCurrent() {
		return getCurData();
	}

	public String getDataName() {
		return getCurData().getClass().getName();
	}

	public void transferItem() {
	}
}
