//$Id: Transfer.java,v 1.1 2007-3-24 上午10:48:25 chaostone Exp $
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

package org.beanfuse.transfer;

import java.util.Locale;

/**
 * 导入导出数据转换器
 * 
 * @author chaostone
 * 
 */
public interface Transfer {

	public static final String EXCEL = "excel";

	public static final String CSV = "csv";

	public static final String TXT = "txt";

	public static final String DBF = "dbf";

	public static final String PDF = "pdf";

	public static final String HTML = "html";

	/**
	 * 启动转换
	 */
	public void transfer(TransferResult tr);

	/**
	 * 转换一个对象
	 * 
	 */
	public void transferItem();

	/**
	 * 添加转换监听器
	 * 
	 * @param listener
	 */
	public Transfer addListener(TransferListener listener);

	/**
	 * 转换数据的类型
	 * 
	 * @return
	 */
	public String getFormat();

	/**
	 * 转换使用的locale
	 * 
	 * @return
	 */
	public Locale getLocale();

	/**
	 * 转换数据的名称
	 * 
	 * @return
	 */
	public String getDataName();

	/**
	 * 得到转换过程中失败的个数
	 * 
	 * @return
	 */
	public int getFail();

	/**
	 * 得到转换过程中成功的个数
	 * 
	 * @return
	 */
	public int getSuccess();

	/**
	 * 查询正在转换的对象的次序号,从1开始
	 * 
	 * @return
	 */
	public int getTranferIndex();

	/**
	 * 返回方前正在转换成的对象
	 * 
	 * @return
	 */
	public Object getCurrent();
}
