//$Id:ResultBuilderImpl.java 2009-1-21 上午12:43:34 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.plugin.result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beanfuse.struts2.route.Action;
import org.beanfuse.struts2.route.ActionNameBuilder;
import org.beanfuse.struts2.route.ProfileService;
import org.beanfuse.struts2.route.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.inject.Inject;

/**
 * 按照各种result的特征进行结果构建<br>
 * 1)chain:/xxx?param1=value1&param2=value2<br>
 * 2)redirect:/yyy??param1=value1&param2=value2<br>
 * 3)path/to/page/page.ftl<br>
 * 
 * @author chaostone
 * 
 */
public class DefaultResultBuilder implements ResultBuilder {

	private Logger log = LoggerFactory.getLogger(DefaultResultBuilder.class);

	protected Map resultTypeConfigs;

	@Inject
	protected ObjectFactory objectFactory;

	@Inject
	protected Configuration configuration;

	@Inject
	protected ViewMapper viewMapper;

	@Inject
	protected ProfileService profileService;

	@Inject
	protected ActionNameBuilder actionNameBuilder;

	public Result build(String resultCode, ActionConfig actionConfig, ActionContext context) {
		String path = null;
		ResultTypeConfig resultTypeConfig = null;
		Map params = new HashMap();
		log.debug("result code:{} for actionConfig:{}", resultCode, actionConfig);
		if (null == resultTypeConfigs) {
			PackageConfig pc = configuration.getPackageConfig(actionConfig.getPackageName());
			this.resultTypeConfigs = pc.getAllResultTypeConfigs();
		}
		// prefix
		// TODO jsp,vm,ftl
		if (!StringUtils.contains(resultCode, ':')) {
			String className = context.getActionInvocation().getProxy().getAction().getClass()
					.getName();
			String methodName = context.getActionInvocation().getProxy().getMethod();
			if (StringUtils.isEmpty(resultCode)) {
				resultCode = "index";
			}
			StringBuilder buf = new StringBuilder();
			buf.append(viewMapper.getViewPath(className, methodName, resultCode));
			buf.append('.');
			buf.append(profileService.getProfile(className).getViewExtension());
			path = buf.toString();
			resultTypeConfig = (ResultTypeConfig) resultTypeConfigs.get("freemarker");
		} else {
			String prefix = StringUtils.substringBefore(resultCode, ":");
			resultTypeConfig = (ResultTypeConfig) resultTypeConfigs.get(prefix);
			String redirectParamStr = null;
			if (prefix.startsWith("redirect")) {
				redirectParamStr = ServletActionContext.getRequest().getParameter("params");
			}
			Action action = (Action) ActionContext.getContext().getContextMap().get(
					"dispatch_action");
			if (null != action) {
				if (null != action.getClazz()) {
					action.setName(actionNameBuilder.build(action.getClazz().getName()));
				}
				if (StringUtils.isBlank(action.getName())) {
					String t_path = ServletActionContext.getRequest().getServletPath();
					if (StringUtils.isBlank(t_path)) { // 在websphere中，t_path会是空串，这是一个workaround
						t_path = ServletActionContext.getRequest().getRequestURI();
						t_path = t_path.substring(t_path.indexOf('/', 1));
						action.setName(t_path);
					} else
						action.setName(t_path);
				}
				path = buildAction(action, params, redirectParamStr);
			} else {
				path = buildAction(StringUtils.substringAfter(resultCode, ":"), params,
						redirectParamStr);
			}

		}
		return buildResult(path, resultCode, resultTypeConfig, context, params);
	}

	/**
	 * 依据beanfuse-action构建
	 * 
	 * @param action
	 * @param params
	 * @param redirectParams
	 * @return
	 */
	private String buildAction(Action action, Map params, String redirectParams) {
		if (StringUtils.isNotEmpty(redirectParams)) {
			action.addParams(redirectParams);
		}
		params.put("method", action.getMethod());
		if (null != action.getParams() && !action.getParams().isEmpty()) {
			params.put("requestParameters", action.getParams());
		}
		return addNamespaceAction(action.getName(), params);
	}

	private String addNamespaceAction(String path, Map<String, Object> param) {
		param.put("namespace", "/");
		int startIndex = 0;
		if (path.charAt(0) == '/') {
			startIndex = 1;
		}
		String actionName = null;
		int endIndex = path.indexOf('!');
		if (-1 == endIndex) {
			endIndex = path.indexOf('.');
		}
		if (-1 == endIndex) {
			endIndex = path.length();
		}
		actionName = path.substring(startIndex, endIndex);
		param.put("actionName", actionName);
		return actionName;
	}

	/**
	 * 依据跳转路径进行构建
	 * 
	 * @param path
	 * @param param
	 * @param redirectParamStr
	 * @return
	 */
	private String buildAction(String path, Map<String, Object> param, String redirectParamStr) {
		String newPath = path;
		if (path.startsWith("?")) {
			newPath = ServletActionContext.getRequest().getServletPath() + path;
		}
		// 添加额外的重定向参数
		if (null != redirectParamStr) {
			if (newPath.indexOf('?') == -1) {
				newPath += ('?' + redirectParamStr.substring(1));
			} else {
				newPath += redirectParamStr;
			}
		}
		String paramString = StringUtils.substringAfter(newPath, "?");
		if (null != paramString) {
			Map requestParams = new HashMap();
			paramString = StringUtils.replace(paramString, "?", "");
			String[] paramPairs = StringUtils.split(paramString, "&");
			for (int i = 0; i < paramPairs.length; i++) {
				String name = StringUtils.substringBefore(paramPairs[i], "=");
				String value = StringUtils.substringAfter(paramPairs[i], "=");
				if (name == "method") {
					param.put("method", value);
				}
				requestParams.put(name, value);
			}
			if (!requestParams.isEmpty()) {
				param.put("requestParameters", requestParams);
			}
		}
		return addNamespaceAction(newPath, param);
	}

	/**
	 * 构建结果
	 * 
	 * @param defaultResultParam
	 * @param resultCode
	 * @param resultTypeConfig
	 * @param context
	 * @param extraParams
	 * @return
	 */
	protected Result buildResult(String defaultResultParam, String resultCode,
			ResultTypeConfig resultTypeConfig, ActionContext context, Map extraParams) {
		Map params = new LinkedHashMap();
		if (resultTypeConfig.getParams() != null) {
			params.putAll(resultTypeConfig.getParams());
		}
		params.put(resultTypeConfig.getDefaultResultParam(), defaultResultParam);
		params.putAll(extraParams);
		ResultConfig resultConfig = new ResultConfig.Builder(resultCode, resultTypeConfig
				.getClassName()).addParams(params).build();
		if (log.isDebugEnabled()) {
			log.debug("result code:{} for resultConfig:{}", resultCode, resultConfig);
			log.debug("location:{}", resultConfig.getLocation());
			log.debug("name:{}", resultConfig.getName());
			log.debug("className:{}", resultConfig.getClassName());
			for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				log.debug("key:{} ==>{}", key, params.get(key));
			}
		}
		try {
			return objectFactory.buildResult(resultConfig, context.getContextMap());
		} catch (Exception e) {
			throw new XWorkException("Unable to build convention result", e, resultConfig);
		}
	}

	public ObjectFactory getObjectFactory() {
		return objectFactory;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setViewMapper(ViewMapper viewMapper) {
		this.viewMapper = viewMapper;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	public void setActionNameBuilder(ActionNameBuilder actionNameBuilder) {
		this.actionNameBuilder = actionNameBuilder;
	}

}
