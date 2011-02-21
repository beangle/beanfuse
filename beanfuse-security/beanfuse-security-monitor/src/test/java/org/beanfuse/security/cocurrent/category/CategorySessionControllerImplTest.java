package org.beanfuse.security.cocurrent.category;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.beanfuse.security.Authentication;
import org.beanfuse.security.UserDetails;
import org.beanfuse.security.concurrent.MemSessionRegistry;
import org.beanfuse.security.concurrent.SessionRegistry;
import org.beanfuse.security.concurrent.category.CategorySessionControllerImpl;
import org.beanfuse.security.dao.SingleSessionProfileProvider;
import org.beanfuse.security.model.UserCategory;
import org.beanfuse.security.online.CategoryProfile;
import org.beanfuse.security.online.SessionProfile;
import org.beanfuse.security.providers.UserNamePasswordAuthentication;
import org.beanfuse.test.spring.SpringTestCase;
import org.testng.annotations.Test;

public class CategorySessionControllerImplTest extends SpringTestCase{

	@Test
	public void testCalculateOnline() {
		SessionProfile sessionProfile =new org.beanfuse.security.online.model.SessionProfile();
		int profileNum = 10;
		int sessionNum = 10000;
		for (int i = 0; i < profileNum; i++) {
			CategoryProfile profile = new org.beanfuse.security.online.model.CategoryProfile();
			UserCategory category = new UserCategory();
			category.setId(new Long(i));
			category.setName(String.valueOf(i));
			profile.setCategory(category);
			profile.setCapacity(10000);
			sessionProfile.getCategoryProfiles().put(category.getId(),profile);
		}

		
		SingleSessionProfileProvider profileProvider = new SingleSessionProfileProvider();
		profileProvider.setProfile(sessionProfile);
		SessionRegistry registry = new MemSessionRegistry();
		for (int i = 0; i < sessionNum; i++) {
			String random = RandomStringUtils.random(21);
			Authentication authentication = new UserNamePasswordAuthentication(random, random);
			UserDetails detail = new UserDetails();
			UserCategory category = new UserCategory();
			category.setId(new Long(RandomUtils.nextInt(profileNum)));
			detail.setCategory(category);
			authentication.setDetails(detail);
			registry.register(random, authentication);
		}

		CategorySessionControllerImpl controller = new CategorySessionControllerImpl();
		controller.setProfileProvider(profileProvider);
		controller.setSessionRegistry(registry);
		controller.loadProfiles();
		for (int i = 0; i < 5; i++) {
			long start = System.currentTimeMillis();
			controller.calculateOnline();
			long elipse1 = System.currentTimeMillis() - start;
			// start = System.currentTimeMillis();
			// controller.reCalculateOnline();
			// long elipse2 = System.currentTimeMillis() - start;
			System.out.println(+sessionNum + "*" + profileNum + " consume:" + elipse1);
		}
	}
}
