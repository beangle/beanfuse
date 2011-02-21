package org.beanfuse.bean.comparators;

import org.apache.commons.lang.StringUtils;

/**
 * 多个属性的比较
 * 
 * @author chaostone
 * 
 */
public class MultiPropertyComparator extends ChainComparator {

	public MultiPropertyComparator(final String propertyStr) {
		super();
		final String[] properties = StringUtils.split(propertyStr, ",");
		for (int i = 0; i < properties.length; i++) {
			addComparator(new PropertyComparator(properties[i].trim()));
		}
	}

}
