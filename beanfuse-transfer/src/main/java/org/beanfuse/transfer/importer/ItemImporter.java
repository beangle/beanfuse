//$Id: SimpleLineImporter.java,v 1.1 2007-3-24 下午12:20:34 chaostone Exp $
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
 *chaostone      2007-3-24         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.importer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.beanfuse.transfer.ItemTransfer;
import org.beanfuse.transfer.importer.reader.ItemReader;
import org.beanfuse.transfer.importer.reader.Reader;

/**
 * 线性导入实现
 * 
 * @author chaostone
 * 
 */
public abstract class ItemImporter extends AbstractImporter implements Importer, ItemTransfer {

	/** 属性说明[attr,description] */
	protected Map descriptions = new HashMap();
	/** 导入属性 */
	protected String[] attrs;
	/** 当前导入值[attr,value] */
	protected Map values = new HashMap();

	/**
	 * 导入之前读入标题
	 */
	public void beforeImport() {
		String[] descs = ((ItemReader) reader).readDescription();
		attrs = ((ItemReader) reader).readTitle();
		for (int i = 0; i < attrs.length && i < descs.length; i++) {
			descriptions.put(attrs[i], descs[i]);
		}
	}

	/**
	 * 设置数据读取对象
	 * 
	 */
	public void setReader(Reader reader) {
		if (reader instanceof ItemReader) {
			this.reader = (ItemReader) reader;
		} else {
			throw new RuntimeException("Expected LineReader but：" + reader.getClass().getName());
		}
	}

	/**
	 * 改变现有某个属性的值
	 * 
	 * @param attr
	 * @param value
	 * @return
	 */
	public void changeCurValue(String attr, Object value) {
		values.put(attr, value);
	}

	public boolean read() {
		Object[] curData = (Object[]) reader.read();
		if (null == curData) {
			setCurrent(null);
			setCurData(null);
			return false;
		} else {
			for (int i = 0; i < curData.length; i++) {
				values.put(attrs[i], curData[i]);
			}
			return true;
		}
	}

	public boolean isDataValid() {
		boolean valid = false;
		for (Object value : values.values()) {
			if (value instanceof String) {
				String tt = (String) value;
				if (StringUtils.isNotBlank(tt)) {
					valid = true;
					break;
				}
			} else {
				if (null != value)
					valid = true;
				break;
			}
		}
		return valid;
	}

	public Map getCurData() {
		return values;
	}

	public void setCurData(Map curData) {
		this.values = curData;
	}

	public String[] getAttrs() {
		return attrs;
	}

	public Map getDescriptions() {
		return descriptions;
	}

	public String processAttr(String attr) {
		return attr;
	}
}
