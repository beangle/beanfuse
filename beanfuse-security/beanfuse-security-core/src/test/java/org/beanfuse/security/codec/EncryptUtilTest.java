package org.beanfuse.security.codec;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author chaostone
 * 
 */
public class EncryptUtilTest {

	@Test
	public void testencode() throws Exception {
		String password = "1";
		String encrypted = "c4ca4238a0b923820dcc509a6f75849b";
		// System.out.println(EncryptUtil.encode(password, "SHA"));
		Assert.assertEquals(EncryptUtil.encode(password, "MD5"), encrypted);
		Assert.assertEquals(EncryptUtil.encode("830826"), "92f9c92d38fd87bdaeae685f9be36183");
	}

}
