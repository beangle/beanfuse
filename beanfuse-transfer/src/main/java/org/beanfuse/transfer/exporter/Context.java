//$Id: Context.java,v 1.1 2007-3-24 上午10:36:06 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-24         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.exporter;

import java.util.HashMap;
import java.util.Map;

public class Context {

	Map datas = new HashMap();

	public Map getDatas() {
		return datas;
	}

	public void setDatas(Map datas) {
		this.datas = datas;
	}

	public void put(String key, Object obj) {
		datas.put(key, obj);
	}

	public Object get(String key) {
		return datas.get(key);
	}

}
