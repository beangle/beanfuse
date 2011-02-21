package org.beanfuse.rule.engine;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.RuleBase;

/**
 * 规则引擎<br>
 * 具体负责执行规则
 * 
 * @author chaostone
 * 
 */
public interface Engine {

	public void execute(Context context);

	public void setPatternMatcher(PatternMatcher matcher);

	public PatternMatcher getPatternMatcher();

	public RuleBase getRuleBase();

	public void setRuleBase(RuleBase base);

	public RuleExecutorBuilder getRuleExecutorBuilder();

	public void setRuleExecutorBuilder(RuleExecutorBuilder executorBuilder);

}
