package org.beanfuse.notification.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.beanfuse.notification.Message;
import org.beanfuse.notification.MessageQueue;

//$Id:DefaultMessageQueue.java Mar 22, 2009 9:08:49 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class DefaultMessageQueue implements MessageQueue {
	
	private Buffer contextBuffer = BufferUtils.synchronizedBuffer(new UnboundedFifoBuffer());;

	public List getMessages() {
		return new ArrayList(contextBuffer);
	}

	public void addMessage(Message message) {
		contextBuffer.add(message);
	}

	public void addMessages(List contexts) {
		contextBuffer.addAll(contexts);
	}

	public Message remove() {
		return (Message) contextBuffer.remove();
	}

	public int size() {
		return contextBuffer.size();
	}

}
