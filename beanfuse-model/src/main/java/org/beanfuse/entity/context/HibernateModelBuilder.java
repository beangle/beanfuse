//$Id:HibernateModelBuilder.java Feb 23, 2009 11:15:05 PM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.entity.context;

import org.beanfuse.entity.Model;
import org.beanfuse.entity.ModelBuilder;
import org.hibernate.SessionFactory;

public class HibernateModelBuilder implements ModelBuilder {

	private SessionFactory sessionFactory;

	public void build() {
		HibernateEntityContext entityContext = new HibernateEntityContext();
		entityContext.initFrom(sessionFactory);
		Model.setContext(entityContext);
	}

	public Model getModel() {
		return Model.getInstance();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
