package org.beanfuse.collection;

import java.util.ArrayList;
import java.util.List;

public final class ListUtils {

	private ListUtils() {
	}

	/**
	 * 将一个集合按照固定大小查分成若干个集合。
	 * 
	 * @param list
	 * @param count
	 * @return
	 */
	public static List split(final List list, final int count) {
		List subIdLists = new ArrayList();
		if (list.size() < count) {
			subIdLists.add(list);
		} else {
			int i = 0;
			while (i < list.size()) {
				int end = i + count;
				if (end > list.size()) {
					end = list.size();
				}
				subIdLists.add(list.subList(i, end));
				i += count;
			}
		}
		return subIdLists;
	}
}
