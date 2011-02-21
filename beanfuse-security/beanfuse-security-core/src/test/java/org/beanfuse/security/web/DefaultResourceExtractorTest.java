package org.beanfuse.security.web;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class DefaultResourceExtractorTest  {

	@Test
	public void testExtract() throws Exception {
		assertEquals(new DefaultResourceExtractor().extract("a.jsp"), "a");
		assertEquals(new DefaultResourceExtractor().extract("/b.do"), "b");
		assertEquals(new DefaultResourceExtractor().extract("/c/d.action"), "c/d");
		assertEquals(new DefaultResourceExtractor().extract("c/d.action"), "c/d");
		assertEquals(new DefaultResourceExtractor().extract("//c/d.action"), "c/d");
		assertEquals(new DefaultResourceExtractor().extract("  //c/d.action "), "c/d");
		assertEquals(new DefaultResourceExtractor().extract("  c/d.action "), "c/d");
		
		assertEquals(new DefaultResourceExtractor().extract("  c/d!remove.action "), "c/d");
	}
}
