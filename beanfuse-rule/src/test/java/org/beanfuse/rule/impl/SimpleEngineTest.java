package org.beanfuse.rule.impl;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.Rule;
import org.beanfuse.rule.RuleBase;
import org.beanfuse.rule.engine.Engine;
import org.beanfuse.rule.engine.RuleExecutorBuilder;
import org.beanfuse.rule.engine.impl.DefaultRuleExecutorBuilder;
import org.beanfuse.rule.engine.impl.FullPatternMatcher;
import org.beanfuse.rule.engine.impl.SimpleEngine;
import org.beanfuse.rule.model.BusinessRule;
import org.beanfuse.rule.model.SimpleContext;
import org.beanfuse.test.spring.SpringTestCase;

public class SimpleEngineTest extends SpringTestCase {

	public void testEngine() {
		Context context = new SimpleContext();
     	Engine engine = new SimpleEngine();
		RuleBase ruleBase = new TestRuleBase();
//		Rule rule1 = new BusinessRule();
//		rule1.setFactory(DefaultRuleExecutorBuilder.SPRING);
//		rule1.setServiceName("ruleExecutor1");

		Rule rule2 = new BusinessRule();
		rule2.setFactory(DefaultRuleExecutorBuilder.BEAN);
		rule2.setServiceName("org.beanfuse.rule.impl.RuleExecutor2");

//		ruleBase.getRules().add(rule1);
		ruleBase.getRules().add(rule2);
		engine.setRuleExecutorBuilder((RuleExecutorBuilder) applicationContext
				.getBean("ruleExecutorBuilder"));
		engine.setRuleBase(ruleBase);
		engine.setPatternMatcher(new FullPatternMatcher());
		engine.execute(context);
	}
}
