package org.beanfuse.webapp.avatar.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.archiver.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAvatarBase implements AvatarBase {
	private static final Logger logger = LoggerFactory.getLogger(AbstractAvatarBase.class);

	// 支持的照片类型
	protected Set<String> types = new HashSet<String>();

	protected List<String> typeList = new ArrayList<String>();

	{
		for (String name : new String[] { "jpg", "JPG", "jpeg", "png" }) {
			types.add(name);
			typeList.add(name);
		}
	}

	protected static final String DEFAULT_AVATAR = "default.jpg";

	public int updateAvatarBatch(File zipFile) {
		String tmpPath = System.getProperty("java.io.tmpdir") + "/avatar/";
		logger.debug("unzip avatar to {}", tmpPath);
		ZipUtils.unzip(zipFile, tmpPath);
		File tmpAvatarBase = new File(tmpPath);
		int count = updateFile(tmpAvatarBase);
		logger.debug("removing avatar tmp path {}", tmpPath);
		tmpAvatarBase.delete();
		return count;
	}

	private int updateFile(File path) {
		int count = 0;
		if (path.isDirectory()) {
			String[] fileNames = path.list();
			for (String fileName : fileNames) {
				File file = new File(path.getAbsolutePath() + "/" + fileName);
				if (file.isDirectory()) {
					count += updateFile(file);
					file.delete();
				} else {
					String type = StringUtils.substringAfter(fileName, ".");
					boolean passed = containType(type);
					if (passed) {
						logger.debug("updating avatar by {}", file.getName());
						updateAvatar(StringUtils.substringBefore(fileName, "."), file, type);
						count++;
					}
					file.delete();
				}
			}
		}
		return count;
	}

	public boolean containType(String type) {
		return types.contains(type);
	}

	public Set<String> getTypes() {
		return types;
	}

	public void setTypes(Set<String> types) {
		this.types = types;
	}

	public File getDefaultAvatar() {
		return getAvatar(DEFAULT_AVATAR);
	}
}
