package org.beanfuse.webapp.avatar.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于文件系统的照片库
 * 
 * @author chaostone
 * 
 */
public class FileSystemAvatarBase extends AbstractAvatarBase {

	private static final Logger logger = LoggerFactory.getLogger(FileSystemAvatarBase.class);

	// 照片路径
	private String avatarDir;

	public List getAvatarNames() {
		File file = new File(avatarDir);
		if (!file.exists()) {
			return Collections.EMPTY_LIST;
		}
		String[] names = file.list();
		List fileNames = new ArrayList();
		for (int i = 0; i < names.length; i++) {
			String name = StringUtils.substringBefore(names[i], ".");
			String ext = StringUtils.substringAfter(names[i], ".");
			if (StringUtils.isNotBlank(name) && containType(ext)) {
				fileNames.add(name);
			}
		}
		Collections.sort(fileNames);
		return fileNames;
	}

	/**
	 * 根据名称和类型得到文件绝对路径
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	private String getAbsoluteAvatarPath(String name, String type) {
		StringBuilder sb = new StringBuilder(avatarDir);
		sb.append(name).append('.').append(type.toLowerCase());
		return sb.toString();
	}

	public File getAvatar(String name) {
		if (StringUtils.contains(name, '.')) {
			File file = new File(avatarDir + name);
			if (file.exists()) {
				return file;
			}
		} else {
			for (int i = 0; i < typeList.size(); i++) {
				StringBuilder sb = new StringBuilder(avatarDir);
				sb.append(name).append('.').append(typeList.get(i));
				File file = new File(sb.toString());
				if (file.exists()) {
					return file;
				}
			}
		}
		return null;
	}

	public boolean updateAvatar(String userName, File avatar, String type) {
		try {
			FileUtils.copyFile(avatar, new File(getAbsoluteAvatarPath(userName, type)));
		} catch (IOException e) {
			logger.error("copy avator error", e);
			return false;
		}
		return true;
	}

	public String getAvatarDir() {
		return avatarDir;
	}

	public void setAvatarDir(String avatarDir) {
		this.avatarDir = avatarDir;
	}

	public String getDescription() {
		return "FileSystemAvatarBase:" + avatarDir;
	}

}
