//$Id: TemplateWriter.java,v 1.1 2007-3-24 下午08:56:55 chaostone Exp $
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

import java.net.URL;

public interface TemplateWriter extends Writer {

	public static final String TEMPLATE_PATH = "template";

	public URL getTemplate();

	public void write();
}
