//$Id: TransferResult.java,v 1.1 2007-3-16 下午09:39:24 chaostone Exp $
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
import java.util.Iterator;
import java.util.List;

public class TransferResult {
	List msgs = new ArrayList();

	List errs = new ArrayList();

	Transfer transfer;

	public void addFailure(String message, Object value) {
		errs.add(new TransferMessage(transfer.getTranferIndex(), message, value));
	}

	public void addMessage(String message, Object value) {
		msgs.add(new TransferMessage(transfer.getTranferIndex(), message, value));
	}

	public boolean hasErrors() {
		return !errs.isEmpty();
	}

	public void printResult() {
		for (Iterator iter = msgs.iterator(); iter.hasNext();) {
			TransferMessage msg = (TransferMessage) iter.next();
			System.out.println(msg);

		}
	}

	public int errors() {
		return errs.size();
	}

	public List getMsgs() {
		return msgs;
	}

	public void setMsgs(List msgs) {
		this.msgs = msgs;
	}

	public List getErrs() {
		return errs;
	}

	public void setErrs(List errs) {
		this.errs = errs;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

}
