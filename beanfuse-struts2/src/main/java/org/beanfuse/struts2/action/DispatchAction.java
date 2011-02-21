//$Id:DispatchActionSupport.java 2009-1-19 下午08:04:06 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.action;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.struts2.route.Action;
import org.beanfuse.struts2.route.Convention;
import org.beanfuse.struts2.route.Flash;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DispatchAction extends ActionSupport {

	protected String forward() {
		return SUCCESS;
	}

	protected String forward(String view) {
		return view;
	}

	protected String forward(String view, String message) {
		addActionMessage(getText(message));
		return view;
	}

	/**
	 * @param action
	 * @return
	 */
	protected String forward(Action action) {
		ActionContext.getContext().getContextMap().put("dispatch_action", action);
		return "chain:dispatch_action";
	}

	protected String forward(Action action, String message) {
		addActionMessage(getText(message));
		return forward(action);
	}

	/**
	 * @param method
	 * @param message
	 * @param params
	 * @return
	 */
	protected String redirect(String method, String message, String params) {
		return redirect(new Action((String) null, method, params), message);
	}

	/**
	 * @param method
	 * @param message
	 * @return
	 */
	protected String redirect(String method, String message) {
		return redirect(new Action(method), message);
	}

	protected String redirect(Action action, String message) {
		if (StringUtils.isNotEmpty(message)) {
			addFlashMessage(message);
		}
		ActionContext.getContext().getContextMap().put("dispatch_action", action);
		return "redirectAction:dispatch_action" ;
	}

	protected void addMessage(String msgKey) {
		addActionMessage(getText(msgKey));
	}

	protected void addError(String msgKey) {
		addActionError(getText(msgKey));
	}

	protected void addFlashMessage(String msgKey) {
		getFlash().addMessage(getText(msgKey));
	}

	protected Flash getFlash() {
		Flash flash = (Flash) ActionContext.getContext().getSession().get("flash");
		if (null == flash) {
			flash = new Flash();
			ActionContext.getContext().getSession().put("flash", flash);
		}
		return flash;
	}

	/**
	 * 将flash中的消息转移到actionmessage<br>
	 * 不要将flash和message混合使用。
	 */
	public Collection<String> getActionMessages() {
		Flash flash = getFlash();
		List<String> messages = (List) flash.get(Flash.MESSAGES);
		if (null != messages) {
			for (String msg : messages) {
				addActionMessage(msg);
			}
			messages.clear();
		}
		return super.getActionMessages();
	}

	/**
	 * 将flash中的错误转移到actionerror<br>
	 * 不要将flash和error混合使用。
	 */
	public Collection<String> getActionErrors() {
		Flash flash = getFlash();
		List<String> errors = (List) flash.get(Flash.ERRORS);
		if (null != errors) {
			for (String msg : errors) {
				addActionError(msg);
			}
			errors.clear();
		}
		return super.getActionErrors();
	}

}
