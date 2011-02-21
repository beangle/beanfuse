//$Id: ParamEditor.java 2008-8-5 下午04:14:08 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      2008-8-5  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.restriction;

import org.beanfuse.model.Component;

public interface ParamEditor extends Component {

	public String getSource();

	public void setSource(String source);

	public String getIdProperty();

	public void setIdProperty(String idProperty);

	public String getProperties();

	public void setProperties(String properties);
}
