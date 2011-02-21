//$Id: PropertyTransformer.java,v 1.1 2007-4-20 上午11:40:14 chaostone Exp $
/*
 * Copyright c 2005-2009
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-4-20         Created
 *  
 ********************************************************************************/

package org.beanfuse.collection.transformers;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;

/**
 * bean属性提取器<br>
 * CollectionUtls.transform(collections,new PropertyTransformer('myAttr'))
 * 
 * @author chaostone
 * 
 */
public class PropertyTransformer implements Transformer {

	private String property;

	public PropertyTransformer(final String property) {
		super();
		this.property = property;
	}

	public PropertyTransformer() {
		super();
	}

	public Object transform(final Object arg0) {
		try {
			return PropertyUtils.getProperty(arg0, property);
		} catch (Exception e) {
			return null;
		}
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(final String property) {
		this.property = property;
	}

}
