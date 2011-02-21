package org.beanfuse.security.concurrent.category;

import java.util.Collection;
import java.util.List;

import org.beanfuse.security.User;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.concurrent.ConcurrentSessionController;
import org.beanfuse.security.online.CategoryProfile;

public interface CategorySessionController extends ConcurrentSessionController {
	/**
	 * 查询允许登陆用户的最大值
	 */
	public int getMax();

	/**
	 * 设置某一类用户的最大用户数
	 * 
	 * @param category
	 * @return
	 */
	public int getMax(UserCategory category);

	/**
	 * 是否到达最大用户
	 * 
	 * @return
	 */
	public boolean isMaxArrived();

	/**
	 * 是否某类用户到达最大数
	 * 
	 * @return
	 */
	public boolean isMaxArrived(UserCategory category);

	/**
	 * 在线用户数
	 */
	public int getOnlineCount();

	/**
	 * 查询某类用户的在线人数
	 * 
	 * @param category
	 * @return
	 */
	public int getOnlineCount(UserCategory category);

	/**
	 * 某用户的在线信息
	 * 
	 * @param category
	 * @return
	 */
	public Collection getOnlineActivities(User user);

	/**
	 * 切换身份
	 * 
	 * @param userId
	 * @param category
	 *            切换身份
	 * @return
	 */
	public void changeCategory(String sessionId, UserCategory category);

	/**
	 * 加载或更新用户配置
	 */
	public void loadProfiles();

	/**
	 * 查询在线用户配置信息
	 * 
	 * @return
	 */
	public List getOnlineProfiles();

	/**
	 * 设定某类用户的配置
	 * 
	 * @param category
	 * @param count
	 */
	public void setCategoryProfile(UserCategory category, CategoryProfile profile);

	/**
	 * 查询某类用户的配置
	 * 
	 * @param category
	 * @return
	 */
	public CategoryProfile getCategoryProfile(UserCategory category);

}