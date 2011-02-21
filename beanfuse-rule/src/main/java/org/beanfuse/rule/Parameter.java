package org.beanfuse.rule;

import java.util.Set;

/**
 * 规则对应参数
 * @author chaostone
 *
 */
public interface Parameter {

	public Set getChildren();

	public void setChildren(Set subRuleParams);

	public Parameter getParent();

	public void setParent(Parameter superRuleParameter);

	public Rule getRule();

	public void setRule(Rule businessRule);

	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

}