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
 * chaostone             2006-7-16            Created
 *  
 ********************************************************************************/
package org.beanfuse.lang;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class SeqStringUtilTest {
	@Test
	public void testIsEqualSeq() {
		String first = "123,4546,";
		String second = ",4546,123";
		assertTrue(SeqStringUtil.isEqualSeq(first, second));
		assertTrue(SeqStringUtil.isEqualSeq(first, second, ","));
	}

	@Test
	public void testMergeSeq() {
		String first = ",1,2,";
		String second = "3,";
		String third = "";
		String forth = null;
		assertTrue(SeqStringUtil.isEqualSeq(SeqStringUtil.mergeSeq(first, second), ",1,2,3,"));
		assertTrue(SeqStringUtil.isEqualSeq(SeqStringUtil.mergeSeq(first, second), ",1,2,3,"));
		assertTrue(SeqStringUtil.isEqualSeq(SeqStringUtil.mergeSeq(first, third), ",1,2,"));
		assertTrue(SeqStringUtil.isEqualSeq(SeqStringUtil.mergeSeq(first, forth), ",1,2,"));
	}

	@Test
	public void testSplitNumSeq() throws Exception {
		String a = "1-2,3,5-9,3,";
		Integer[] nums = SeqStringUtil.splitNumSeq(a);
		assertEquals(nums.length, 8);
	}

	@Test
	public void testMisc() {
		assertEquals(",2,", SeqStringUtil.subtractSeq("1,2", "1"));
		assertFalse(SeqStringUtil.isEqualSeq(",2005-9,", ",2005-9,2006-9,"));
	}
}
