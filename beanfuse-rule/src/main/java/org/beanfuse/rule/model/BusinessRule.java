package org.beanfuse.rule.model;

import java.util.HashSet;
import java.util.Set;

import org.beanfuse.model.pojo.LongIdTimeObject;
import org.beanfuse.rule.Rule;

public class BusinessRule extends LongIdTimeObject implements Rule {

    private static final long serialVersionUID = -3648535746761474692L;

    /** 规则名称 */
    private String name;
    
    /** 适用业务 */
    private String business;

    /** 规则描述 */
    private String description;

    /** 规则管理容器 */
    private String factory;

    /** 规则服务名 */
    private String serviceName;

    /** 规则参数集合 */
    private Set params = new HashSet();
    
    /** 是否启用 */
    private boolean enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Set getParams() {
		return params;
	}

	public void setParams(Set params) {
		this.params = params;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
