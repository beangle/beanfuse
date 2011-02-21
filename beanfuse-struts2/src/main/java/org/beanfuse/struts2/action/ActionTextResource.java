package org.beanfuse.struts2.action;

import java.util.Locale;

import org.beanfuse.text.AbstractTextResource;
import org.beanfuse.text.TextResource;

import com.opensymphony.xwork2.ActionSupport;

public class ActionTextResource extends AbstractTextResource implements TextResource {

	ActionSupport action;

	public ActionTextResource(ActionSupport action) {
		super();
		this.action = action;
	}

	public Locale getLocale() {
		return action.getLocale();
	}

	public String getText(String key, Object[] args) {
		String[] params = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			params[i] = String.valueOf(args[i]);
		}
		return action.getText(key, params);
	}

	public String getText(String key) {
		return action.getText(key);
	}

	public void setLocale(Locale locale) {

	}

}
