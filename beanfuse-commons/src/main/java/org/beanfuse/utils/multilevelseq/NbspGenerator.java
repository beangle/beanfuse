//$Id: NbspGenerator.java 2008-8-18 下午05:20:25 asus Exp $
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
 * Name           Date          Description 
 * ============  ============        ============
 *chaostone      2008-8-4         Created
 *  
 ********************************************************************************/

package org.beanfuse.utils.multilevelseq;

public class NbspGenerator {

	public String generator(int repeat) {
		String repeater = "&nbsp;";
		StringBuilder returnval = new StringBuilder();
		for (int i = 0; i < repeat; i++) {
			returnval.append(repeater);
		}
		return returnval.toString();
	}
}
