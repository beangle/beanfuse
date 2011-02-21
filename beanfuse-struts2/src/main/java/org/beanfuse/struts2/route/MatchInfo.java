package org.beanfuse.struts2.route;

/**
 * action匹配信息
 * 
 * @author chaostone
 * 
 */
public class MatchInfo {

	/** last char index of matcher */
	int startIndex;

	StringBuilder reserved = new StringBuilder(0);

	public MatchInfo(int startIndex) {
		super();
		this.startIndex = startIndex;
	}

	public StringBuilder getReserved() {
		return reserved;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public String toString() {
		return reserved.toString();
	}

}
