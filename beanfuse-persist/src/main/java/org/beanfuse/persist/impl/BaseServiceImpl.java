package org.beanfuse.persist.impl;

import org.beanfuse.persist.EntityDao;
import org.beanfuse.persist.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseServiceImpl {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected EntityService entityService;
	protected EntityDao entityDao;

	public EntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
