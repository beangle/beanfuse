package org.beanfuse.webapp.avatar.service;

import java.io.File;
import java.util.List;

public class DataBaseAvatarBase extends AbstractAvatarBase {

	public File getAvatar(String name) {
		return null;
	}

	public boolean updateAvatar(String name, File avatar, String type) {
		return false;
	}

	public List getAvatarNames() {
		return null;
	}

	public String getDescription() {
		return "DataBaseAvatarBase";
	}

}
