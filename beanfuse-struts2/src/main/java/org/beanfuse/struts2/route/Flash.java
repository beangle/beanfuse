package org.beanfuse.struts2.route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Flash implements Map, Serializable {

	public static final String MESSAGES = "messages";
	public static final String ERRORS = "errors";

	/**
	 * current request
	 */
	public final HashMap now = new HashMap();

	/**
	 * next request
	 */
	public final HashMap next = new HashMap();

	/**
	 * return now and session saved
	 * 
	 * @return
	 */
	public Set keySet() {
		return now.keySet();
	}

	/**
	 * return now and session saved value
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		return now.get(key);
	}

	/**
	 * put value to next
	 * 
	 * @param key
	 * @param value
	 */
	public Object put(Object key, Object value) {
		return next.put(key, value);
	}

	public void putAll(Map values) {
		next.putAll(values);
	}

	void keep(String key) {
		next.put(key, now.get(key));
	}

	void keep() {
		next.putAll(now);
	}

	public void nextToNow() {
		now.clear();
		now.putAll(next);
		next.clear();
	}

	public void clear() {
		now.clear();
	}

	public boolean containsKey(Object key) {
		return now.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return now.containsValue(value);
	}

	public Set entrySet() {
		return now.entrySet();
	}

	public boolean isEmpty() {
		return now.isEmpty();
	}

	public Object remove(Object key) {
		return now.remove(key);
	}

	public int size() {
		return now.size();
	}

	public Collection values() {
		return now.values();
	}

	public void addMessage(String message) {
		List messages = (List) get(Flash.MESSAGES);
		if (null == messages) {
			messages = new ArrayList();
			put(Flash.MESSAGES, messages);
		}
		messages.add(message);
	}
}
