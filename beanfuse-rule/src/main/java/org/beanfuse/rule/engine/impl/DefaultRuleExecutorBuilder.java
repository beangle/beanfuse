package org.beanfuse.rule.engine.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beanfuse.entity.Model;
import org.beanfuse.model.EntityUtils;
import org.beanfuse.rule.Rule;
import org.beanfuse.rule.engine.RuleExecutor;
import org.beanfuse.rule.engine.RuleExecutorBuilder;
import org.beanfuse.rule.model.RuleConfig;
import org.beanfuse.rule.model.RuleConfigParam;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DefaultRuleExecutorBuilder implements RuleExecutorBuilder, ApplicationContextAware {

    ApplicationContext appContext;

    public static final String SPRING = "spring";

    public static final String BEAN = "bean";

    public RuleExecutor build(Rule rule) {
        if (SPRING.equals(rule.getFactory())) {
            return (RuleExecutor) appContext.getBean(rule.getServiceName());
        } else if (BEAN.equals(rule.getFactory())) {
            try {
                return (RuleExecutor) Class.forName(rule.getServiceName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public RuleExecutor build(List rules, boolean stopWhenFail) {
        CompositeExecutor composite = new CompositeExecutor();
        composite.setStopWhenFail(stopWhenFail);
        for (Iterator iter = rules.iterator(); iter.hasNext();) {
            Rule rule = (Rule) iter.next();
            composite.add(build(rule));
        }
        return composite;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    public RuleExecutor build(RuleConfig ruleConfig) {
        RuleExecutor executor = build(ruleConfig.getRule());
        if (null == executor) {
            return null;
        }
        Map map = new HashMap();
        for (Iterator iter = ruleConfig.getParams().iterator(); iter.hasNext();) {
            RuleConfigParam param = (RuleConfigParam) iter.next();
            map.put(param.getParam().getName(), param.getValue());
        }
        Model.populate(map, executor);
        return executor;
    }

}
