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
 * chaostone             2006-3-20            Created
 *  
 ********************************************************************************/
package org.beanfuse.lang;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class StringUtilTest {

	@Test
	public void testCountStr() {
		String targetStr = "11001101101111";
		String searchStr = "11";
		assertEquals(StringUtil.countStr(targetStr, searchStr), 5);
	}

	@Test
	public void testUnCamel() {
		assertEquals(StringUtil.unCamel("MyCountInI_cbc", '-'), "my-count-ini_cbc");
		assertEquals(StringUtil.unCamel("MyCounT", '_'), "my_count");
		assertEquals(StringUtil.unCamel("MYCOUNT", '-'), "mycount");
		assertEquals(StringUtil.unCamel("parent_id", '_'), "parent_id");
		assertEquals(StringUtil.unCamel("parentId", '_'), "parent_id");
	}
}
