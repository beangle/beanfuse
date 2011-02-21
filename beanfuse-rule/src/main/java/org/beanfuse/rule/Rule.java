package org.beanfuse.rule;

import java.util.Set;

public interface Rule {

    public static final String ELECTBUSINESS = "elect";

    public static final String AUDITTBUSINESS = "audit";

    public Set getParams();

    public void setParams(Set ruleParams);

    public boolean isEnabled();

    public void setEnabled(boolean enabled);

    public String getName();

    public void setName(String name);

    public String getBusiness();

    public void setBusiness(String business);

    public String getDescription();

    public void setDescription(String description);

    public String getFactory();

    public void setFactory(String factory);

    public String getServiceName();

    public void setServiceName(String serviceName);

}