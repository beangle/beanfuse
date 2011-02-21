package org.beanfuse.persist;

import java.util.ArrayList;
import java.util.Collection;

import org.testng.annotations.Test;

public class EntityDaoTest {

	@Test
	public void testFoo() {
		Collection aa = new ArrayList();
		new EntityDaoTest().saveOrUpdate(aa);
	}

	public void saveOrUpdate(Object a) {

	}
}
