//$Id: TransferMessage.java,v 1.1 2007-3-16 下午09:20:34 chaostone Exp $
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
 *chaostone      2007-3-16         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 转换消息
 * 
 * @author chaostone
 * 
 */
public class TransferMessage {

	public static String ERROR_ATTRS = "error.transfer.attrs";

	public static String ERROR_ATTRS_EXPORT = "error.transfer.attrs.export";

	/**
	 * 转换数据的序号
	 */
	int index;

	/**
	 * 消息内容
	 */
	String message;

	/**
	 * 消息中使用的对应值
	 */
	List values = new ArrayList();

	public TransferMessage(int index, String message, Object value) {
		this.index = index;
		this.message = message;
		this.values.add(value);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List getValues() {
		return values;
	}

	public void setValues(List values) {
		this.values = values;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("index", this.index)
				.append("message", this.message).append("values", this.values).toString();
	}

}
