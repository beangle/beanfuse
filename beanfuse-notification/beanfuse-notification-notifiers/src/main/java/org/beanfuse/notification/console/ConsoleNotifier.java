package org.beanfuse.notification.console;

import org.beanfuse.notification.Message;
import org.beanfuse.notification.NotificationException;
import org.beanfuse.notification.Notifier;

//$Id:ConsoleNotifier.java Mar 22, 2009 11:35:45 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class ConsoleNotifier implements Notifier {

	public String getType() {
		return "console";
	}

	public void sendMessage(Message context) throws NotificationException {
		System.out.println(context.getText());
	}

}
