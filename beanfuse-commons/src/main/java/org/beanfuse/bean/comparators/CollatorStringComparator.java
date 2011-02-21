package org.beanfuse.bean.comparators;

import java.text.Collator;

public class CollatorStringComparator implements StringComparator {
	private boolean asc;

	private Collator collator;

	public CollatorStringComparator() {
		super();
		collator = Collator.getInstance();
	}

	public CollatorStringComparator(final boolean asc) {
		this();
		this.asc = asc;
	}

	public CollatorStringComparator(final boolean asc, final Collator collator) {
		this.collator = collator;
		this.asc = asc;
	}

	public int compare(final Object what0, final Object what1) {
		return (asc ? 1 : -1)
				* (collator.compare((null == what0) ? "" : what0.toString(),
						(null == what1) ? "" : what1.toString()));
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(final boolean asc) {
		this.asc = asc;
	}

	public Collator getCollator() {
		return collator;
	}

	public void setCollator(final Collator collator) {
		this.collator = collator;
	}

}
