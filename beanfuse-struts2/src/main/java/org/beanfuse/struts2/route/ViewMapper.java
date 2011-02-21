//$Id:ResultCodePageMapper.java 2009-1-19 上午12:11:02 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.route;

/**
 * viewname -> 页面路径的映射
 * 
 * @author chaostone
 * 
 */
public interface ViewMapper {

	public static final String FULL = "full";

	public static final String SEO = "seo";

	public static final String SIMPLE = "simple";

	String getViewPath(String className, String methodName, String viewName);
}
