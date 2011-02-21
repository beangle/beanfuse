//$Id: AbstractItemWriter.java 2008-7-8 下午08:26:58 chaostone Exp $
/*
 *
 * Copyright c 2005-2009.
 * 
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
 * ============   ============  ============
 * chaostone      2008-7-8  Created
 *  
 ********************************************************************************/
package org.beanfuse.transfer.exporter.writer;

import java.io.OutputStream;

import org.beanfuse.transfer.exporter.Context;

public abstract class AbstractItemWriter implements ItemWriter {
	protected OutputStream outputStream;

	protected Context context;

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
