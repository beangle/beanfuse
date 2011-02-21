package org.beanfuse.webapp.avatar.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.archiver.ZipUtils;
import org.beanfuse.collection.page.PagedList;
import org.beanfuse.security.User;
import org.beanfuse.struts2.route.Action;
import org.beanfuse.webapp.avatar.service.AvatarBase;
import org.beanfuse.webapp.security.action.SecurityAction;

/**
 * 用户头像管理
 * 
 * @author chaostone
 * 
 */
public class BoardAction extends SecurityAction {

	protected AvatarBase avatarBase;

	public String index() {
		List names = avatarBase.getAvatarNames();
		PagedList page = new PagedList(names, getPageLimit());
		put("names", page);
		put("avatarBase", avatarBase);
		return forward();
	}

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

	public String upload() throws Exception {
		File[] files = (File[]) getParams().get("avatar");
		String userName = get("user.name");
		if (files.length > 0) {
			String type = StringUtils.substringAfter(get("avatarFileName"), ".");
			boolean passed = avatarBase.containType(type);
			if (passed) {
				avatarBase.updateAvatar(userName, files[0], type);
			} else {
				return forward("upload");
			}
		}
		return redirect(new Action(UserAction.class, "info", "&user.name=" + userName),
				"info.save.success");
	}

	public String uploadBatch() throws Exception {
		File[] files = (File[]) getParams().get("avatar");
		String msg = "info.save.success";
		if (files.length > 0) {
			if (ZipUtils.isZipFile(files[0])) {
				avatarBase.updateAvatarBatch(files[0]);
			} else {
				msg = "error.wrongzipfile";
			}
		}
		return redirect("index", msg);
	}

	public void setAvatarBase(AvatarBase avatarBase) {
		this.avatarBase = avatarBase;
	}
}
