//$Id: Writer.java,v 1.1 2007-3-24 下午08:47:06 chaostone Exp $
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

package org.beanfuse.transfer.exporter.writer;

import java.io.OutputStream;

import org.beanfuse.transfer.exporter.Context;

public interface Writer {

	public String getFormat();

	public void setContext(Context context);

	public OutputStream getOutputStream();

	public void setOutputStream(OutputStream outputStream);

	public void close();
}
