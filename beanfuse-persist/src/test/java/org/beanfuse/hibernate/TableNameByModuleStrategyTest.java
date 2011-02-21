package org.beanfuse.hibernate;

import org.beanfuse.persist.hibernate.TableNameByModuleStrategy;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TableNameByModuleStrategyTest {

	@Test
	public void testGetSchemaName() {
		Assert.assertEquals("security_online", TableNameByModuleStrategy
				.getSchema("org.beanfuse.security.online.model"));

		Assert.assertEquals("sys_", TableNameByModuleStrategy.getPrefix("org.beanfuse.security"));
	}

	public static void main(String[] args) {
		System.out
				.println(TableNameByModuleStrategy.getSchema("org.beanfuse.security.online.model"));
	}
}
