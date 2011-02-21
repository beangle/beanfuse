package org.beanfuse.rule;

import java.util.List;
import java.util.Map;

import org.beanfuse.text.Message;
/**
 * 规则执行上下文
 * @author chaostone
 *
 */
public interface Context {
	
	public Map getParams();
	
	public List getMessages();
	
	public void addMessage(Message message);
	
	public List getErrors();
	
	public void addError(Message message);
}
