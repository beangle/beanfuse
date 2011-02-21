package org.beanfuse.security.access.log;

import org.beanfuse.security.access.AccessLog;

public class UserAccessLog extends DefaultAccessLog implements AccessLog {

	Object user;

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

}
