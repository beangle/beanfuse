package org.beanfuse.rule.engine.impl;

import java.util.List;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.RuleBase;
import org.beanfuse.rule.engine.Agenda;
import org.beanfuse.rule.engine.Engine;
import org.beanfuse.rule.engine.PatternMatcher;
import org.beanfuse.rule.engine.RuleExecutor;
import org.beanfuse.rule.engine.RuleExecutorBuilder;

public class SimpleEngine implements Engine {

	protected PatternMatcher matcher;

	protected RuleBase base;

	protected RuleExecutorBuilder executorBuilder;

	protected boolean stopWhenFail = false;

	public void execute(Context context) {
		Agenda agenda = matcher.buildAgenda(base, context);
		List rules = agenda.getRules();
		RuleExecutor executor = executorBuilder.build(rules, stopWhenFail);
		executor.execute(context);
	}

	public PatternMatcher getPatternMatcher() {
		return matcher;
	}

	public void setPatternMatcher(PatternMatcher matcher) {
		this.matcher = matcher;
	}

	public RuleBase getRuleBase() {
		return base;
	}

	public void setRuleBase(RuleBase base) {
		this.base = base;
	}

	public RuleExecutorBuilder getRuleExecutorBuilder() {
		return executorBuilder;
	}

	public void setRuleExecutorBuilder(RuleExecutorBuilder executorBuilder) {
		this.executorBuilder = executorBuilder;
	}

	public boolean isStopWhenFail() {
		return stopWhenFail;
	}

	public void setStopWhenFail(boolean stopWhenFail) {
		this.stopWhenFail = stopWhenFail;
	}

}
