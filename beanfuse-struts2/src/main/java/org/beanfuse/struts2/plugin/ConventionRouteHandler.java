//$Id:ConventionHandler.java 2009-1-18 下午10:49:46 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.plugin;

import java.util.HashMap;
import java.util.Map;

import org.beanfuse.struts2.plugin.result.ResultBuilder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.inject.Inject;

/**
 * 实现action到result之间的路由和处理<br>
 * 默认按照方法进行路由
 * @author chaostone
 *
 */
public class ConventionRouteHandler implements UnknownHandler {

	@Inject
	protected ResultBuilder resultBuilder;

	public Map getResultTypesByExtension(PackageConfig packageConfig) {
		Map results = packageConfig.getAllResultTypeConfigs();
		Map resultsByExtension = new HashMap();
		resultsByExtension.put("jsp", results.get("dispatcher"));
		resultsByExtension.put("vm", results.get("velocity"));
		resultsByExtension.put("ftl", results.get("freemarker"));
		// Issue 22 - Add html and htm as default result extensions
		resultsByExtension.put("html", results.get("dispatcher"));
		resultsByExtension.put("htm", results.get("dispatcher"));
		return resultsByExtension;
	}

	public ActionConfig handleUnknownAction(String namespace, String actionName)
			throws XWorkException {
		return null;
	}

	public Object handleUnknownActionMethod(Object arg0, String arg1) throws NoSuchMethodException {
		return null;
	}

	public Result handleUnknownResult(ActionContext actionContext, String actionName,
			ActionConfig actionConfig, String resultCode) throws XWorkException {
		return resultBuilder.build(resultCode, actionConfig, actionContext);
	}
}
