package org.beanfuse.bean.comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 组合比较器
 * 
 * @author chaostone
 * 
 */
public class ChainComparator implements Comparator {

	private List comparators;

	public int compare(final Object first, final Object second) {
		int cmpRs = 0;
		for (Iterator iterator = comparators.iterator(); iterator.hasNext();) {
			final Comparator com = (Comparator) iterator.next();
			cmpRs = com.compare(first, second);
			if (0 == cmpRs) {
				continue;
			} else {
				break;
			}
		}
		return cmpRs;
	}

	public ChainComparator() {
		this.comparators = new ArrayList();
	}

	public ChainComparator(final List comparators) {
		super();
		this.comparators = comparators;
	}

	public void addComparator(final Comparator com) {
		this.comparators.add(com);
	}

	public List getComparators() {
		return comparators;
	}

	public void setComparators(final List comparators) {
		this.comparators = comparators;
	}

}
