package org.beanfuse.rule.model;

import java.util.ArrayList;
import java.util.List;

import org.beanfuse.entity.Model;
import org.beanfuse.rule.Context;
import org.beanfuse.rule.Rule;
import org.beanfuse.rule.engine.RuleExecutor;
import org.beanfuse.rule.engine.RuleExecutorBuilder;
import org.beanfuse.rule.engine.impl.DefaultRuleExecutorBuilder;
import org.beanfuse.test.spring.SpringTestCase;

public class RuleTest extends SpringTestCase {

//	public void testSpringBuilder() {
//		RuleExecutorBuilder builder = (DefaultRuleExecutorBuilder) applicationContext
//				.getBean("ruleExecutorBuilder");
//		Rule rule = (Rule) Model.newInstance(Rule.class);
//		rule.setFactory(DefaultRuleExecutorBuilder.SPRING);
//		rule.setServiceName("ruleExecutor1");
//		RuleExecutor exceutor = builder.build(rule);
//		Context context = new SimpleContext();
//		exceutor.execute(context);
//	}

	public void testComposite() {
		RuleExecutorBuilder builder = (DefaultRuleExecutorBuilder) applicationContext
				.getBean("ruleExecutorBuilder");
		List rules = new ArrayList();
//		Rule rule1 = (Rule) Model.newInstance(Rule.class);
//		rule1.setFactory(DefaultRuleExecutorBuilder.SPRING);
//		rule1.setServiceName("ruleExecutor1");
		
		Rule rule2 = new BusinessRule();
		rule2.setFactory(DefaultRuleExecutorBuilder.BEAN);
		rule2.setServiceName("org.beanfuse.rule.impl.RuleExecutor2");
		
//		rules.add(rule1);
		rules.add(rule2);
		
		Context context = new SimpleContext();
		RuleExecutor exceutor = builder.build(rules,false);
		exceutor.execute(context);
	}
}
