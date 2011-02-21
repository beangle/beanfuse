//$Id: Transfer.java,v 1.1 2007-3-16 下午09:25:39 chaostone Exp $
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

package org.beanfuse.transfer.importer;

import java.util.Map;

import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.importer.reader.Reader;

/**
 * 数据转换接口
 * 
 * @author chaostone
 * 
 */
public interface Importer extends Transfer {
	/**
	 * 是否忽略空值
	 * 
	 * @return
	 */
	public boolean ignoreNull();

	/**
	 * 设置数据读取对象
	 * 
	 */
	public void setReader(Reader reader);

	/**
	 * 读取数据，设置内部步进状态等
	 * 
	 * @return
	 */
	public boolean read();

	/**
	 * 当前读入的数据是否有效
	 * 
	 * @return
	 */
	public boolean isDataValid();

	/**
	 * 在导入一个数据之前的hook函数
	 * 
	 */
	public void beforeImportItem();

	/**
	 * 在导入全部数据的hook函数
	 * 
	 */
	public void beforeImport();

	/**
	 * 设置当前正在转换的对象
	 * 
	 * @param object
	 */
	public void setCurrent(Object object);

	/**
	 * 返回现在正在转换的原始数据
	 * 
	 * @return
	 */
	public Object getCurData();

	/**
	 * 设置正在转换的对象
	 * 
	 * @param curData
	 */
	public void setCurData(Map curData);

}
