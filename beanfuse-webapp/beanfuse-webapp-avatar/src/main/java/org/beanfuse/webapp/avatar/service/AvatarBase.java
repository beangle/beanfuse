package org.beanfuse.webapp.avatar.service;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * 照片库
 * 
 * @author chaostone
 * 
 */
public interface AvatarBase {

	public List getAvatarNames();

	public File getAvatar(String name);

	public boolean updateAvatar(String name, File avatar, String type);

	public int updateAvatarBatch(File zipFile);

	public File getDefaultAvatar();

	public Set<String> getTypes();

	public String getDescription();

	boolean containType(String type);
}
