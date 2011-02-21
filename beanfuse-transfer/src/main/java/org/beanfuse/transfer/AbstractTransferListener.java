//$Id: AbstractTransferListener.java,v 1.1 2007-3-17 下午03:44:02 chaostone Exp $
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

public class AbstractTransferListener implements TransferListener {
	protected Transfer transfer;

	public void endTransfer(TransferResult tr) {
	}

	public void endTransferItem(TransferResult tr) {
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public void startTransfer(TransferResult tr) {

	}

	public void startTransferItem(TransferResult tr) {
	}

}
