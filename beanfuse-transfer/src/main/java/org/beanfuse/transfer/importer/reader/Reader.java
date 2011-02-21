//$Id: TransferReader.java,v 1.1 2007-3-24 下午12:05:07 chaostone Exp $
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

package org.beanfuse.transfer.importer.reader;

/**
 * 数据读取类
 * 
 * @author chaostone
 * 
 */
public interface Reader {

	/**
	 * 读取数据
	 * 
	 * @return
	 */
	public Object read();

	/**
	 * 返回读取类型的格式
	 * 
	 * @return
	 */
	public String getFormat();
}
