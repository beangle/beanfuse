package org.beanfuse.rule.engine;

import java.util.List;

import org.beanfuse.rule.Rule;
import org.beanfuse.rule.model.RuleConfig;

public interface RuleExecutorBuilder {

	public RuleExecutor build(Rule rule);

	public RuleExecutor build(List rules,boolean stopWhenFail);
    

    public RuleExecutor build(RuleConfig ruleConfig);

}
