//$Id:DefaultModelBuilder.java Feb 23, 2009 11:03:57 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.entity.context;

import java.io.InputStream;
import java.util.Properties;

import org.beanfuse.entity.EntityContext;
import org.beanfuse.entity.Model;
import org.beanfuse.entity.ModelBuilder;
import org.beanfuse.entity.Populator;

public class DefaultModelBuilder implements ModelBuilder {

	private static final String POPULATOR_PRO = "model.populatorClass";

	private static final String CONTEXT_PRO = "model.contextClass";

	/**
	 * 初始化默认对象
	 */
	public void build() {
		try {
			Properties props = new Properties();
			InputStream is = AbstractEntityContext.class
					.getResourceAsStream("/org/beanfuse/entity/model-default.properties");
			if (null != is) {
				props.load(is);
			}
			is = AbstractEntityContext.class
					.getResourceAsStream("/model.properties");
			if (null != is) {
				props.load(is);
			}
			if (null == Model.getContext()) {
				Class contextClass = Class.forName((String) props
						.get(CONTEXT_PRO));
				Model.setContext((EntityContext) contextClass.newInstance());
			}
			if (null == Model.getPopulator()) {
				Class populatorClass = Class.forName((String) props
						.get(POPULATOR_PRO));
				Model.setPopulator((Populator) populatorClass.newInstance());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Model getModel() {
		return Model.getInstance();
	}

}
