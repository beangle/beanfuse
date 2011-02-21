package org.beanfuse.notification.console;

import org.beanfuse.notification.Notifier;
import org.beanfuse.notification.SimpleMessage;
import org.testng.annotations.Test;

//$Id:ConsoleNotifierTest.java Mar 22, 2009 11:45:02 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class ConsoleNotifierTest {

	@Test
	public void testSendMessage() throws Exception {
		Notifier notifier = new ConsoleNotifier();
		SimpleMessage context = new SimpleMessage();
		context.setText("hello world");
		notifier.sendMessage(context);
	}
}
