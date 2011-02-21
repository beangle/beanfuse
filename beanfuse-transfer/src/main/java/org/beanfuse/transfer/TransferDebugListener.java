//$Id: TransferDebugListener.java,v 1.1 2007-3-17 下午12:40:10 chaostone Exp $
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
 *chaostone      2007-3-17         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer;

/**
 * 转换调试监听器
 * 
 * @author chaostone
 *  
 */
public class TransferDebugListener extends AbstractTransferListener {

	Transfer transfer;

	public void startTransfer(TransferResult tr) {
		tr.addMessage("start", transfer.getDataName());
	}

	public void endTransfer(TransferResult tr) {
		tr.addMessage("end", transfer.getDataName());
	}

	public void startTransferItem(TransferResult tr) {
		tr.addMessage("start Item", transfer.getTranferIndex() + "");
	}

	public void endTransferItem(TransferResult tr) {
		tr.addMessage("end Item", transfer.getCurrent());
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

}
