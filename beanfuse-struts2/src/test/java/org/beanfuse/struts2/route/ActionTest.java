//$Id:ActionTest.java 2009-1-22 上午01:24:04 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.route;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

@Test
public class ActionTest {

	public void testGetNamespaceAndName() throws Exception {
		Action action = new Action("method");
		Action controllerAction = new Action("/home/person", "search");

		assertNull(action.getNamespace());
		assertEquals("/home/", controllerAction.getNamespace());
	}

	public void testUri() {
		Action action = new Action("first/second.action", "add", "&id=1");
		assertEquals("/first/second.action?method=add&id=1", action.getUri());

		Action action1 = new Action("first/second.do", "add", "&id=1");
		assertEquals("/first/second.do?method=add&id=1", action1.getUri());
	}
}
