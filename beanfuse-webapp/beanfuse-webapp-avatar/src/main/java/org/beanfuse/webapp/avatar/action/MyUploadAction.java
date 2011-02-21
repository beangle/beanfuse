package org.beanfuse.webapp.avatar.action;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.security.User;
import org.beanfuse.struts2.route.Action;
import org.beanfuse.webapp.avatar.service.AvatarBase;
import org.beanfuse.webapp.security.action.SecurityAction;

/**
 * 上传照片
 * 
 * @author chaostone
 * 
 */
public class MyUploadAction extends SecurityAction {

	protected AvatarBase avatarBase;

	public String uploadForm() {
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

	public String upload() throws Exception {
		File[] files = (File[]) getParams().get("avatar");
		if (files.length > 0) {
			String type = StringUtils.substringAfter(get("avatarFileName"), ".");
			boolean passed = avatarBase.containType(type);
			if (passed) {
				avatarBase.updateAvatar(getLoginName(), files[0], type);
			} else {
				return forward("upload");
			}
		}
		return redirect(new Action(MyAction.class, "info"), "info.upload.success");
	}

	public void setAvatarBase(AvatarBase avatarBase) {
		this.avatarBase = avatarBase;
	}
}
