package org.beanfuse.notification.msn;

import java.util.Iterator;

import org.beanfuse.notification.Message;
import org.beanfuse.notification.NotificationException;
import org.beanfuse.notification.Notifier;
import org.codehaus.plexus.msn.MsnClient;
import org.codehaus.plexus.msn.MsnException;

//$Id:MsnNotifier.java Mar 23, 2009 8:32:56 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class MsnNotifier implements Notifier {

	private MsnClient msnClient;

	public String getType() {
		return "msn";
	}

	public void sendMessage(Message msg) throws NotificationException {
//		MSNMessenger msn;
//		msn = new MSNMessenger("youraccount@hotmail.com", "password");
//		msn.setInitialStatus(UserStatus.ONLINE);
//		// msn.addMsnListener(new MSNAdapter(msn));
//		msn.login();
//		System.out.println("Waiting for the response....");
		// 捕捉Ctrl+C的输入以便注销MSN的登录
//		Runtime.getRuntime().addShutdownHook(new MSNDaemon());

		try {
			msnClient.login();
			for (Iterator iterator = msg.getRecipients().iterator(); iterator.hasNext();) {
				String recipient = (String) iterator.next();
				msnClient.sendMessage(recipient, msg.getText());
			}
		} catch (MsnException e) {
			throw new RuntimeException("Exception while sending message.", e);
		} finally {
			try {
				msnClient.logout();
			} catch (MsnException e) {

			}
		}

	}

	public MsnClient getMsnClient() {
		return msnClient;
	}

	public void setMsnClient(MsnClient msnClient) {
		this.msnClient = msnClient;
	}

}
