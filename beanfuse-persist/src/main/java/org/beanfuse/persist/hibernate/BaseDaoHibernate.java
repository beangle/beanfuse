package org.beanfuse.persist.hibernate;

import org.beanfuse.persist.EntityDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDaoHibernate extends HibernateDaoSupport {

	protected EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
