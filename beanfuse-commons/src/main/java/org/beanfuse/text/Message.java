package org.beanfuse.text;

import java.util.ArrayList;
import java.util.List;

public class Message {

	private String key;

	private List params = new ArrayList();

	public Message(String key, List params) {
		super();
		this.key = key;
		this.params = params;
	}

	public Message(String key, Object[] objs) {
		super();
		this.key = key;
		if (null != objs) {
			for (int i = 0; i < objs.length; i++) {
				this.params.add(objs[i]);
			}
		}

	}

	public Message(String key) {
		super();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public List getParams() {
		return params;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setParams(List params) {
		this.params = params;
	}

}
