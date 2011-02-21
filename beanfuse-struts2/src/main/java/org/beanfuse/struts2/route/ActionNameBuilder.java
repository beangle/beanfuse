//$Id:ContollerNameBuilder.java 2009-1-18 下午10:33:51 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.route;

public interface ActionNameBuilder {

	public static final String SHORT = "short";

	public static final String SEO = "seo";

	public static final String SIMPLE = "simple";

	/**
	 * 默认类名对应的控制器名称(含有扩展名)
	 * 
	 * @param className
	 * @return
	 */
	public String build(String className);

	public void setProfileService(ProfileService profileService);

}
