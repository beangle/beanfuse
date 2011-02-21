package org.beanfuse.security.web;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class SimpleResourceExtractorTest {

	@Test
	public void testExtract() throws Exception {
		assertEquals(new SimpleResourceExtractor().extract("a.jsp"), "a.jsp");
		assertEquals(new SimpleResourceExtractor().extract("/b.do"), "b.do");
		assertEquals(new SimpleResourceExtractor().extract("/c/d.action?method=aa"), "c/d.action");
	}
}
