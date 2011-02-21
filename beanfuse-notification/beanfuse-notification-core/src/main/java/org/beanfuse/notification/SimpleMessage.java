package org.beanfuse.notification;

import java.util.ArrayList;
import java.util.List;

//$Id:SimpleMessage.java Mar 22, 2009 11:39:55 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class SimpleMessage extends AbstractMessage {

	private List recipients = new ArrayList();

	public SimpleMessage() {
		super();
	}

	public SimpleMessage(String recipient, String subject, String text) {
		recipients.add(recipient);
		setSubject(subject);
		setText(text);
	}

	public List getRecipients() {
		return recipients;
	}

	public String getContentType() {
		return "text/plain";
	}

}
