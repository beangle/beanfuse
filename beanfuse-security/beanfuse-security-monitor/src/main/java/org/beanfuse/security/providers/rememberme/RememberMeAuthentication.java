package org.beanfuse.security.providers.rememberme;

import org.beanfuse.security.providers.UserNamePasswordAuthentication;

public class RememberMeAuthentication extends UserNamePasswordAuthentication {

	public RememberMeAuthentication() {
		super();
	}

	public RememberMeAuthentication(String userName, String password) {
		super(userName, password);
	}

}
