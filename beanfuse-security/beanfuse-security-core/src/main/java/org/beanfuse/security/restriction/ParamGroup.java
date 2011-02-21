package org.beanfuse.security.restriction;

import java.util.Set;

import org.beanfuse.model.LongIdEntity;

public interface ParamGroup extends LongIdEntity {

	public String getName();

	public void setName(String name);

	public Set getParams();

	public void setParams(Set params);

	public Param getParam(String paramName);

}
