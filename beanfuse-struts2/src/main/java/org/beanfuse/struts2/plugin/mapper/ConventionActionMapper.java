//$Id:ConventionActionMapper.java 2009-1-20 下午04:41:44 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.plugin.mapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.beanfuse.struts2.route.Flash;

import com.opensymphony.xwork2.config.ConfigurationManager;

/**
 * 映射URI到struts对应的Action,兼容原有的method形式<br>
 * 1)默认方法更改为index<br>
 * 2)可以接受method=的形式重新指定方法<br>
 * 3)默认使用action!method的方式进行uri生成
 * 
 * @author chaostone
 * 
 */
public class ConventionActionMapper extends DefaultActionMapper implements ActionMapper {

	private static String methodParam = "method";
	/**
	 * reserved method parameter
	 */
	public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
		ActionMapping mapping = super.getMapping(request, configManager);
		if (null != mapping) {
			String method = request.getParameter(methodParam);
			if (StringUtils.isNotEmpty(method)) {
				mapping.setMethod(method);
			} else {
				if ("execute".equals(mapping.getMethod()) || null == mapping.getMethod()) {
					mapping.setMethod("index");
				}
			}
		}
		return mapping;
	}

}
