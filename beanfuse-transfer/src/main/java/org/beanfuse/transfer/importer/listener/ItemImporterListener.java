//$Id: ItemTransferListener.java,v 1.1 2007-3-25 下午05:15:50 chaostone Exp $
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

package org.beanfuse.transfer.importer.listener;

import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.TransferListener;
import org.beanfuse.transfer.TransferResult;
import org.beanfuse.transfer.importer.ItemImporter;

public class ItemImporterListener implements TransferListener {

	protected ItemImporter importer;

	public void endTransfer(TransferResult tr) {
		// TODO Auto-generated method stub

	}

	public void endTransferItem(TransferResult tr) {
		// TODO Auto-generated method stub
	}

	public void setTransfer(Transfer transfer) {
		if (transfer instanceof ItemImporter) {
			this.importer = (ItemImporter) transfer;
		}
	}

	public void startTransfer(TransferResult tr) {
		// TODO Auto-generated method stub
	}

	public void startTransferItem(TransferResult tr) {
		// TODO Auto-generated method stub
	}

}
