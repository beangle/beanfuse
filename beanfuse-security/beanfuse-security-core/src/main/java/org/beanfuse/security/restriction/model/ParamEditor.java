//$Id: ParamEditor.java 2008-8-5 下午04:19:32 chaostone Exp $
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
package org.beanfuse.security.restriction.model;

import java.io.Serializable;

public class ParamEditor implements org.beanfuse.security.restriction.ParamEditor, Serializable {

	private static final long serialVersionUID = -7317257496760882622L;

	private String source;

	private String idProperty;

	private String properties;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

}
