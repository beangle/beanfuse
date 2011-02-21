package org.beanfuse.injection.spring;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

public class DependencyTest {

	@Test
	public void testGetDefinition() {

		ApplicationContext factory = new ClassPathXmlApplicationContext("/applicationContext.xml");

		assertNotNull((UserDaoProvider) factory.getBean("userDaoProvider"));

		assertNotNull((UserLdapProvider) factory.getBean("userLdapProvider"));

		UserService userService = (UserService) factory.getBean("userService");

		assertNotNull(userService);

		assertNotNull(userService.getSomeMap());

		assertEquals(UserLdapProvider.class, userService.getProvider().getClass());

		UserService userLdapService = (UserService) factory.getBean("userLdapService");

		assertEquals(UserLdapProvider.class, userLdapService.getProvider().getClass());

	}
}
