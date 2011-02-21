package org.beanfuse.struts2.route;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.beanfuse.struts2.example.action.FirstAction;
import org.beanfuse.struts2.example.action.anotherNested.ThirdAction;
import org.beanfuse.struts2.example.action.nested.SecondAction;
import org.beanfuse.struts2.route.impl.DefaultActionNameBuilder;
import org.beanfuse.struts2.route.impl.ProfileServiceImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConventionsTest {

	ActionNameBuilder actionNameBuilder;
	ProfileService profileService;

	@BeforeClass
	public void init() {
		actionNameBuilder = new DefaultActionNameBuilder();
		profileService = new ProfileServiceImpl();
		actionNameBuilder.setProfileService(profileService);
	}

	@Test
	public void testGetActionName() throws Exception {
		assertEquals(actionNameBuilder.build(FirstAction.class.getName()), "/first.html");
		assertEquals(actionNameBuilder.build(SecondAction.class.getName()), "second.action");
		assertEquals(actionNameBuilder.build(ThirdAction.class.getName()),
				"/another-nested/third.action");
	}

	@Test
	public void testGetProfile() throws Exception {
		Profile profile = profileService.getProfile(FirstAction.class);
		assertNotNull(profile);
		assertEquals("org.beanfuse.struts2.example.action", profile.getPackageName());
		profile = profileService.getProfile(SecondAction.class);
		assertNotNull(profile);
		assertEquals("org.beanfuse.struts2.example.action.nested", profile.getPackageName());
		profile = profileService.getProfile(ThirdAction.class);
		assertNotNull(profile);
		assertEquals("org.beanfuse.*.anotherNested", profile.getPackageName());
		
		profile = profileService.getProfile("com.company.app.web.action.SomeAction");
		assertNotNull(profile);
		assertEquals("com.company.app.*web.action", profile.getPackageName());
	}

	@Test
	public void testGetInfix() throws Exception {
		Profile profile = new Profile();
		profile.setPattern("org.beanfuse.struts2.example.*.");
		profile.setActionSuffix("Action");
		assertEquals("action/anotherNested/third", profile.getInfix(ThirdAction.class.getName()));
	}

	@Test
	public void testGetSimpleName() {
		Profile profile = new Profile();
		profile.setActionSuffix("Action");
		assertEquals("org/beanfuse/struts2/example/action/anotherNested/third", profile
				.getSimpleName(ThirdAction.class.getName()));

	}
}
