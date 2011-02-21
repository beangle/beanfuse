package org.beanfuse.rule.model;

import java.util.HashSet;
import java.util.Set;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.rule.Parameter;
import org.beanfuse.rule.Rule;

public class RuleParameter extends LongIdObject implements Parameter {

    private static final long serialVersionUID = -5534831174352027516L;

    /** 业务规则 */
    private Rule rule;

    /** 参数名称 */
    private String name;

    /** 参数类型 */
    private String type;
    
    /** 参数标题 */
    private String title;
    
    /** 参数描述 */
    private String description;

    /** 上级参数 */
    private Parameter parent;

    /** 所有的子参数 */
    private Set children = new HashSet();

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Parameter getParent() {
		return parent;
	}

	public void setParent(Parameter parent) {
		this.parent = parent;
	}

	public Set getChildren() {
		return children;
	}

	public void setChildren(Set children) {
		this.children = children;
	}
   
}
