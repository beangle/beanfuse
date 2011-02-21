package org.beanfuse.webapp.avatar.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.beanfuse.security.User;
import org.beanfuse.webapp.avatar.service.AvatarBase;
import org.beanfuse.webapp.security.action.SecurityAction;

/**
 * 查看下载自己的照片
 * 
 * @author chaostone
 * 
 */
public class MyAction extends SecurityAction {

	protected AvatarBase avatarBase;

	public String index() throws IOException {
		String userName = getLoginName();
		File file = avatarBase.getAvatar(userName);
		if (null == file) {
			ServletActionContext.getResponse().getWriter().write(
					"without you avatar [" + userName + "]");
		} else {
			AvatarUtil.copyTo(file, ServletActionContext.getResponse());
		}
		return null;
	}

	public String info() {
		User user = getUser();
		File file = avatarBase.getAvatar(user.getName());
		if (null != file) {
			double length = (double) (file.length() / 1024);
			put("length", new Double(length));
			put("lastModified", new Date(file.lastModified()));
		}
		put("user", user);
		return forward();
	}

	public void setAvatarBase(AvatarBase avatarBase) {
		this.avatarBase = avatarBase;
	}

}
