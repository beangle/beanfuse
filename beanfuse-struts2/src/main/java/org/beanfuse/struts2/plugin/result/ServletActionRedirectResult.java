//$Id:ServletActionRedirectResult.java 2009-1-22 上午12:45:25 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.plugin.result;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.views.util.UrlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.util.reflection.ReflectionException;
import com.opensymphony.xwork2.util.reflection.ReflectionExceptionHandler;

public class ServletActionRedirectResult extends ServletRedirectResult implements
		ReflectionExceptionHandler {

	private static final long serialVersionUID = -9042425229314584066L;

	/** The default parameter */
	public static final String DEFAULT_PARAM = "actionName";

	private static final Logger LOG = LoggerFactory.getLogger(ServletActionRedirectResult.class);

	protected String actionName;
	protected String namespace;
	protected String method;
	protected boolean supressEmptyParameters = false;

	private Map requestParameters = new LinkedHashMap();

	public ServletActionRedirectResult() {
		super();
	}

	public ServletActionRedirectResult(String actionName) {
		this(null, actionName, null);
	}

	public ServletActionRedirectResult(String actionName, String method) {
		this(null, actionName, method);
	}

	public ServletActionRedirectResult(String namespace, String actionName, String method) {
		super(null);
		this.namespace = namespace;
		this.actionName = actionName;
		this.method = method;
	}

	protected List prohibitedResultParam = Arrays.asList(new String[] { DEFAULT_PARAM, "namespace",
			"method", "encode", "parse", "location", "prependServletContext",
			"supressEmptyParameters" });

	/**
	 * @see com.opensymphony.xwork2.Result#execute(com.opensymphony.xwork2.ActionInvocation)
	 */
	public void execute(ActionInvocation invocation) throws Exception {
		actionName = conditionalParse(actionName, invocation);
		if (namespace == null) {
			namespace = invocation.getProxy().getNamespace();
		} else {
			namespace = conditionalParse(namespace, invocation);
		}
		if (method == null) {
			method = "";
		} else {
			method = conditionalParse(method, invocation);
		}

		String resultCode = invocation.getResultCode();
		if (resultCode != null) {
			ResultConfig resultConfig = (ResultConfig) invocation.getProxy().getConfig()
					.getResults().get(resultCode);
			if (null != resultConfig) {
				Map resultConfigParams = resultConfig.getParams();
				for (Iterator i = resultConfigParams.entrySet().iterator(); i.hasNext();) {
					Map.Entry e = (Map.Entry) i.next();
					if (!prohibitedResultParam.contains(e.getKey())) {
						requestParameters.put(e.getKey().toString(), e.getValue() == null ? ""
								: conditionalParse(e.getValue().toString(), invocation));
						String potentialValue = e.getValue() == null ? "" : conditionalParse(e
								.getValue().toString(), invocation);
						if (!supressEmptyParameters
								|| ((potentialValue != null) && (potentialValue.length() > 0))) {
							requestParameters.put(e.getKey().toString(), potentialValue);
						}
					}
				}
			}
		}

		StringBuilder tmpLocation = new StringBuilder(actionMapper
				.getUriFromActionMapping(new ActionMapping(actionName, namespace, method, null)));
		UrlHelper.buildParametersString(requestParameters, tmpLocation, "&");

		setLocation(tmpLocation.toString());

		super.execute(invocation);
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setSupressEmptyParameters(boolean supressEmptyParameters) {
		this.supressEmptyParameters = supressEmptyParameters;
	}

	public ServletActionRedirectResult addParameter(String key, Object value) {
		requestParameters.put(key, String.valueOf(value));
		return this;
	}

	public void handle(ReflectionException ex) {
		LOG.debug(ex.getMessage(), ex);
	}

	public Map getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(Map requestParams) {
		if (null != requestParams)
			this.requestParameters.putAll(requestParams);
	}

}
