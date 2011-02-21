package org.beanfuse.rule.engine.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;
/**
 * 组合规则执行者
 * @author chaostone
 *
 */
public class CompositeExecutor implements RuleExecutor {

	private List executors = new ArrayList();

	/** 是否在单个规则失败后停止 默认为否 */
	private boolean stopWhenFail = false;

	public boolean execute(Context context) {
		boolean result = true;
		for (Iterator iter = executors.iterator(); iter.hasNext();) {
			RuleExecutor executor = (RuleExecutor) iter.next();
			result &= executor.execute(context);
			if (stopWhenFail && !result) {
				return result;
			}
		}
		return result;
	}

	public void add(RuleExecutor executor) {
		executors.add(executor);
	}

	public List getExecutors() {
		return executors;
	}

	public void setExecutors(List executors) {
		this.executors = executors;
	}

	public boolean isStopWhenFail() {
		return stopWhenFail;
	}

	public void setStopWhenFail(boolean stopWhenFailure) {
		this.stopWhenFail = stopWhenFailure;
	}

}
