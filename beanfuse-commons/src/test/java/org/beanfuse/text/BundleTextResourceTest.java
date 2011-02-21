package org.beanfuse.text;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class BundleTextResourceTest {

	@Test
	public void testGetText() {
		Locale locale = new Locale("zh", "CN");
		ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
		assertNotNull(bundle);
		BundleTextResource tr = new BundleTextResource();
		tr.setLocale(locale);
		tr.setBundle(bundle);
		assertEquals(tr.getText("hello.world"), "nihao");
	}
}
