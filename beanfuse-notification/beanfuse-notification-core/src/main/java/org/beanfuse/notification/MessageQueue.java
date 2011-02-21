package org.beanfuse.notification;

import java.util.List;

//$Id:MessageQueue.java Mar 22, 2009 9:04:20 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */

/**
 * 消息队列
 */
public interface MessageQueue {

	int size();

	Message remove();

	List getMessages();

	void addMessages(List contexts);

	void addMessage(Message message);

}
