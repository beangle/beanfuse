package org.beanfuse.notification;

//$Id:Notifier.java Mar 22, 2009 11:12:57 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public interface Notifier {

	String getType();

	void sendMessage(Message message) throws NotificationException;
}
