package org.beanfuse.rule.engine.impl;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.RuleBase;
import org.beanfuse.rule.engine.Agenda;
import org.beanfuse.rule.engine.PatternMatcher;

public class FullPatternMatcher implements PatternMatcher {

	public Agenda buildAgenda(RuleBase base, Context context) {
		return new ListAgenda(base.getRules());
	}

}
