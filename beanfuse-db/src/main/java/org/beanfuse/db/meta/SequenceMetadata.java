package org.beanfuse.db.meta;

public class SequenceMetadata {

	private String name;

	private long start;

	private int increment;

	public SequenceMetadata(String name) {
		super();
		this.name = name;
	}

	public SequenceMetadata() {
		super();
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}
	

}
