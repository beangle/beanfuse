package org.beanfuse.security.dao;

import org.beanfuse.security.online.SessionProfile;

public class SingleSessionProfileProvider implements SessionProfileProvider {

	protected SessionProfile profile;

	public SessionProfile getProfile() {
		return profile;
	}

	public void setProfile(SessionProfile profile) {
		this.profile = profile;
	}

 

}
