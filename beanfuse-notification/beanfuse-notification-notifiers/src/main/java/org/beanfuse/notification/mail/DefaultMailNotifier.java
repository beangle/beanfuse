package org.beanfuse.notification.mail;

import org.beanfuse.notification.Message;

//$Id:DefaultMailNotifier.java Mar 22, 2009 12:36:40 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class DefaultMailNotifier extends AbstractMailNotifier {

	protected String buildSubject(Message context) {
		return context.getSubject();
	}

	protected String buildText(Message context) {
		return context.getText();
	}

}

