package org.beanfuse.rule.impl;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

public class RuleExecutor1 implements RuleExecutor {

	public boolean execute(Context context) {
		System.out.println("I am rule executor No 1");
		return false;
	}

}
