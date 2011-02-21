package org.beanfuse.notification.service;

import java.util.Map;

import org.beanfuse.notification.Notifier;

//$Id:DefaultNotifierService.java Mar 22, 2009 11:27:21 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class DefaultNotifierService implements NotifierService {

	private Map notifiers;

	public Notifier getNotifier(String notifierId) {
		return (Notifier)notifiers.get(notifierId);
	}

	public Map getNotifiers() {
		return notifiers;
	}

	public void setNotifiers(Map notifiers) {
		this.notifiers = notifiers;
	}
}
