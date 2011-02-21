package org.beanfuse.rule.impl;

import java.util.ArrayList;
import java.util.List;

import org.beanfuse.rule.RuleBase;

public class TestRuleBase implements RuleBase {

	List rules = new ArrayList();

	public List getRules() {
		return rules;
	}

}
