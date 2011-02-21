//$Id:SinglePageWapper.java 2009-1-21 下午06:34:15 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.collection.page;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class PageWapper implements Page {

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List getItems() {
		return page.getItems();
	}

	public Iterator iterator() {
		return page.iterator();
	}

	public boolean add(Object obj) {
		return page.add(obj);
	}

	public boolean addAll(Collection datas) {
		return page.addAll(datas);
	}

	public void clear() {
		page.clear();
	}

	public boolean contains(Object obj) {
		return page.contains(obj);
	}

	public boolean containsAll(Collection datas) {
		return page.containsAll(datas);
	}

	public boolean isEmpty() {
		return page.isEmpty();
	}

	public int size() {
		return page.size();
	}

	public Object[] toArray() {
		return page.toArray();
	}

	public Object[] toArray(Object[] datas) {
		return page.toArray(datas);
	}

	public boolean remove(Object obj) {
		return page.remove(obj);
	}

	public boolean removeAll(Collection datas) {
		return page.removeAll(datas);
	}

	public boolean retainAll(Collection datas) {
		return page.retainAll(datas);
	}

	public void add(int arg0, Object arg1) {
		page.add(arg0, arg1);
	}

	public boolean addAll(int arg0, Collection arg1) {
		return page.addAll(arg0, arg1);
	}

	public Object get(int index) {
		return page.get(index);
	}

	public int lastIndexOf(Object o) {
		return page.lastIndexOf(o);
	}

	public ListIterator listIterator() {
		return page.listIterator();
	}

	public ListIterator listIterator(int index) {
		return page.listIterator(index);
	}

	public Object remove(int index) {
		return page.remove(index);
	}

	public Object set(int arg0, Object arg1) {
		return page.set(arg0, arg1);
	}

	public List subList(int fromIndex, int toIndex) {
		return page.subList(fromIndex, toIndex);
	}

	public int indexOf(Object o) {
		return page.indexOf(o);
	}
}
