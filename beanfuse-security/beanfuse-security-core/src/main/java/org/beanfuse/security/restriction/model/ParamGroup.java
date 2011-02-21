package org.beanfuse.security.restriction.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.restriction.Param;

public class ParamGroup extends LongIdObject implements
		org.beanfuse.security.restriction.ParamGroup {

	private static final long serialVersionUID = -5761007041977213647L;

	private String name;

	private Set params = new HashSet(0);

	public Param getParam(String paramName) {
		for (Iterator iterator = params.iterator(); iterator.hasNext();) {
			Param param = (Param) iterator.next();
			if (param.getName().equals(paramName)) {
				return param;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getParams() {
		return params;
	}

	public void setParams(Set params) {
		this.params = params;
	}

}
