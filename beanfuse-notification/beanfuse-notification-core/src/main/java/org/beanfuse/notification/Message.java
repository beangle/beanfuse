package org.beanfuse.notification;

import java.util.List;
import java.util.Properties;

//$Id:Message.java Mar 22, 2009 11:16:31 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public interface Message {

	public static final String TEXT = "text/plain";

	public static final String HTML = "text/html";

	String getSubject();

	void setSubject(String subject);

	String getText();

	void setText(String text);

	Properties getProperties();

	List getRecipients();

	String getContentType();

	void setContentType(String contentType);
}
