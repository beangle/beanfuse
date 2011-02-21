//$Id: DefaultEntityExporter.java,v 1.1 2007-3-25 下午02:46:50 chaostone Exp $
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
 *chaostone      2007-3-25         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.exporter;

import org.beanfuse.transfer.TransferMessage;

public class DefaultEntityExporter extends ItemExporter {

	/**
	 * 导入属性
	 */
	protected String[] attrs;

	/**
	 * 属性提取器
	 */
	protected PropertyExtractor propertyExtractor;

	/**
	 * 转换单个实体
	 */
	public void transferItem() {
		Object[] values = new Object[attrs.length];
		for (int i = 0; i < values.length; i++) {
			try {
				values[i] = propertyExtractor.getPropertyValue(getCurrent(), attrs[i]);
			} catch (Exception e) {
				transferResult.addFailure(TransferMessage.ERROR_ATTRS_EXPORT,
						"occur in get property :" + attrs[i] + " and exception:" + e.getMessage());
			}
		}
		writer.write(values);
	}

	public PropertyExtractor getPropertyExtractor() {
		return propertyExtractor;
	}

	public void setPropertyExtractor(PropertyExtractor propertyExporter) {
		this.propertyExtractor = propertyExporter;
	}

	public String[] getAttrs() {
		return attrs;
	}

	public void setAttrs(String[] attrs) {
		this.attrs = attrs;
	}
}
