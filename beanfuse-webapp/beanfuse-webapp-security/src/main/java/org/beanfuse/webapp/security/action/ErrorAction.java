package org.beanfuse.webapp.security.action;

import org.beanfuse.struts2.action.BaseAction;

/**
 * @author dell
 * 
 */
public class ErrorAction extends BaseAction {

	public String index() {
		if (get("errorCode") != null) {
			addActionError(get("errorCode"));
		}
		return ERROR;
	}

}
