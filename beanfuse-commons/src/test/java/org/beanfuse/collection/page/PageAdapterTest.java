/**
 * 
 */
package org.beanfuse.collection.page;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/**
 * @author zhouqi
 * 
 */
public class PageAdapterTest {
	@Test
	public void testD() throws Exception {
		List datas = new ArrayList(26);
		for (int i = 0; i < 26; i++) {
			datas.add(String.valueOf(i));
		}
		Page page = new PagedList(datas, 20);
		assertNotNull(page.iterator());
		assertEquals(page.iterator().next(), "0");
		page.next();
		assertEquals(page.iterator().next(), "20");
		page.moveTo(2);
		assertEquals(page.iterator().next(), "20");
		page.previous();
		assertEquals(page.iterator().next(), "0");
	}
}
