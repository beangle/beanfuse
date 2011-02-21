//$Id: Exporter.java,v 1.1 2007-3-24 上午10:34:47 chaostone Exp $
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

package org.beanfuse.transfer.exporter;

import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.exporter.writer.Writer;

/**
 * 数据导出转换器
 * 
 * @author chaostone
 * 
 */
public interface Exporter extends Transfer {

	public void setContext(Context context);

	public void setWriter(Writer writer);
}
