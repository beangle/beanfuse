//$Id: TransferListener.java,v 1.1 2007-3-16 下午09:27:20 chaostone Exp $
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

/**
 * 转换监听器
 * 
 * @author chaostone
 * 
 */
public interface TransferListener {

	/**
	 * 开始转换
	 * 
	 */
	public void startTransfer(TransferResult tr);

	/**
	 * 
	 *结束转换
	 */
	public void endTransfer(TransferResult tr);

	/**
	 * 开始转换单个项目
	 * 
	 */
	public void startTransferItem(TransferResult tr);

	/**
	 * 结束转换单个项目
	 * 
	 */
	public void endTransferItem(TransferResult tr);

	/**
	 * 设置转换器
	 * 
	 * @param transfer
	 */
	public void setTransfer(Transfer transfer);
}
