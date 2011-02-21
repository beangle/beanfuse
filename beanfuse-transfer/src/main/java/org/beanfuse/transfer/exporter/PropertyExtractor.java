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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-4-18            Created
 *  
 ********************************************************************************/
package org.beanfuse.transfer.exporter;

import org.beanfuse.text.TextResource;

/**
 * 对象属性输出接口
 * 
 * @author chaostone
 * 
 */
public interface PropertyExtractor {

	public Object getPropertyValue(Object target, String property) throws Exception;

	public TextResource getTextResource();

	public void setTextResource(TextResource textResource);

}
