package org.beanfuse.rule.engine;

import org.beanfuse.rule.Context;

/**
 * 规则执行者
 * 
 * @author chaostone
 * 
 */
public interface RuleExecutor {

	public boolean execute(Context context);
}
