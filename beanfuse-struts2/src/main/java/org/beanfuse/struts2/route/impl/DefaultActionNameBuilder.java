//$Id:DefaultControllerNameBuilder.java 2009-1-18 下午10:35:30 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.route.impl;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.struts2.route.ActionNameBuilder;
import org.beanfuse.struts2.route.Convention;
import org.beanfuse.struts2.route.Profile;
import org.beanfuse.struts2.route.ProfileService;
import org.beanfuse.lang.StringUtil;

import com.opensymphony.xwork2.inject.Inject;

public class DefaultActionNameBuilder implements ActionNameBuilder {

	private ProfileService profileService;

	/**
	 * 根据class对应的profile获得ctl/action类中除去后缀后的名字。<br>
	 * 如果对应profile中是uriStyle,那么类中只保留简单类名，去掉后缀，并且小写第一个字母。<br>
	 * 否则加上包名，其中的.编成URI路径分割符。包名不做其他处理。<br>
	 * 复杂URL,以/开始
	 * 
	 * @param clazz
	 * @return
	 */
	public String build(String className) {
		Profile profile = profileService.getProfile(className);
		StringBuilder sb = new StringBuilder();
		if (SHORT.equals(profile.getUriStyle())) {
			String simpleName = className.substring(className.lastIndexOf('.') + 1);
			sb.append(StringUtils.uncapitalize(simpleName.substring(0, simpleName.length()
					- profile.getActionSuffix().length())));
		} else if (SIMPLE.equals(profile.getUriStyle())) {
			sb.append(Convention.separator).append(profile.getInfix(className));
		} else if (SEO.equals(profile.getUriStyle())) {
			sb.append(Convention.separator).append(StringUtil.unCamel(profile.getInfix(className)));
		} else {
			throw new RuntimeException("unsupported uri style " + profile.getUriStyle());
		}
		if (null != profile.getUriExtension()) {
			sb.append('.').append(profile.getUriExtension());
		}
		return sb.toString();
	}

	@Inject
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

}
