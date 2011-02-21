//$Id: LineWriter.java,v 1.1 2007-3-24 下午08:50:11 chaostone Exp $
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

public interface ItemWriter extends Writer {

	public void write(Object obj);

	public void writeTitle(Object title);

}
