package org.beanfuse.lang;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class BitStringUtilTest {

	@Test
	public void binValueOf() {
		assertEquals(BitStringUtil
				.binValueOf("00000000000000000000000000000000011111111111111111111"), 1048575);

		assertEquals(BitStringUtil
				.binValueOf("00000000000000000000000000000000000011100000000000000"), 114688);
	}
}
