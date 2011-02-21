package org.beanfuse.rule.engine.impl;

import java.util.ArrayList;
import java.util.List;

import org.beanfuse.rule.engine.Agenda;
/**
 * 顺序布置的规则执行顺序
 * @author chaostone
 *
 */
public class ListAgenda implements Agenda {

	private List rules = new ArrayList();
	
	public ListAgenda() {
		super();
	}

	public ListAgenda(List rules) {
		this.rules=rules;
	}

	public List getRules() {
		return rules;
	}

	public void setRules(List rules) {
		this.rules = rules;
	}

}
