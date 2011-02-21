package org.beanfuse.struts2.route;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class ProfileTest {

	@Test
	public void testExactlyMatchIndex() throws Exception {
		Profile profile = new Profile();
		profile.setPattern("org.beanfuse.example.action");
		assertTrue("org.beanfuse.example.action".length() - 1 == profile
				.getCtlMatchInfo("org.beanfuse.example.action").startIndex);
	}

	@Test
	public void testMatchIndex() throws Exception {
		Profile profile = new Profile();
		profile.setPattern("org.beanfuse.example.*.web.action");
		assertTrue("org.beanfuse.example.d.web.action".length() - 1 == profile
				.getCtlMatchInfo("org.beanfuse.example.d.web.action").startIndex);

		profile = new Profile();
		profile.setPattern("org.beanfuse.example.*.web.action.");
		assertTrue("org.beanfuse.example.ddd.aa.web.action.".length() - 1 == profile
				.getCtlMatchInfo("org.beanfuse.example.ddd.aa.web.action.AAAction").startIndex);
	}

	@Test
	public void testFailMatch() throws Exception {
		Profile profile = new Profile();
		profile.setPattern("org.beanfuse.example.*.web.action");
		assertTrue(-1 == profile.getCtlMatchInfo("org.beanfuse.example.d.dd").startIndex);

		profile = new Profile();
		profile.setPattern("org.beanfuse.example.*.web.action.");
		assertTrue("org.beanfuse.example.ddd.aa.web.action.".length() - 1 == profile
				.getCtlMatchInfo("org.beanfuse.example.ddd.aa.web.action.AAAction").startIndex);
	}

	@Test
	public void testPressMatch() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			Profile profile = new Profile();
			profile.setPattern("org.beanfuse.example.*.web.action");
			assertTrue(-1 == profile.getCtlMatchInfo("org.beanfuse.example.d.dd").startIndex);

			profile = new Profile();
			profile.setPattern("org.beanfuse.example.ddd.aa.web.action.");
			assertTrue("org.beanfuse.example.ddd.aa.web.action.".length() - 1 == profile
					.getCtlMatchInfo("org.beanfuse.example.ddd.aa.web.action.AAAction").startIndex);
		}
		// System.out.println(System.currentTimeMillis() - start);
	}
}
