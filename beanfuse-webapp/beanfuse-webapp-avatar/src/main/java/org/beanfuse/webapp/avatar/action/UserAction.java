package org.beanfuse.webapp.avatar.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beanfuse.security.User;
import org.beanfuse.webapp.avatar.service.AvatarBase;
import org.beanfuse.webapp.security.action.SecurityAction;

/**
 * 管理照片
 * 
 * @author chaostone
 * 
 */
public class UserAction extends SecurityAction {

	protected AvatarBase avatarBase;

	/**
	 * 查看照片信息
	 */
	public String info() {
		String userName = get("user.name");
		if (StringUtils.isEmpty(userName)) {
			return null;
		}
		List users = entityService.load(User.class, "name", userName);
		User user = null;
		if (users.isEmpty()) {
			return null;
		} else {
			user = (User) users.get(0);
		}

		File file = avatarBase.getAvatar(user.getName());
		if (null != file) {
			double length = (double) (file.length() / 1024);
			put("length", new Double(length));
			put("lastModified", new Date(file.lastModified()));
		}
		put("user", user);
		return forward();
	}

	/**
	 * 只显示头像
	 * 
	 * @return
	 * @throws IOException
	 */
	public String show() throws IOException {
		String userName = get("user.name");
		if (StringUtils.isEmpty(userName)) {
			return null;
		}
		File file = avatarBase.getAvatar(userName);
		if (null == file) {
			file = avatarBase.getDefaultAvatar();
		}
		AvatarUtil.copyTo(file, ServletActionContext.getResponse());
		return null;
	}

	public void setAvatarBase(AvatarBase avatarBase) {
		this.avatarBase = avatarBase;
	}
}
