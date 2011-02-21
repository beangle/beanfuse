//$Id:ResultConfigBuilder.java 2009-1-20 下午11:38:04 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.plugin.result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.ActionConfig;

/**
 * 为构建自定义的结果，抽象出的一个接口
 * 
 * @author chaostone
 * 
 */
public interface ResultBuilder {

	public Result build(String resultCode, ActionConfig actionConfig, ActionContext context);
}
