//$Id: EntityTransfer.java,v 1.1 2007-3-16 下午09:29:35 chaostone Exp $
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
 *chaostone      2007-3-16         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.importer;

import org.beanfuse.entity.Model;
import org.beanfuse.entity.types.EntityType;

public class DefaultEntityImporter extends MultiEntityImporter {

	private static final String alias = "_entity";

	public DefaultEntityImporter() {
		super();
	}

	public DefaultEntityImporter(Class entityClass) {
		EntityType type = null;
		if (entityClass.isInterface()) {
			type = Model.getEntityType(entityClass.getName());
		} else {
			type = Model.getEntityType(entityClass);
		}
		entityTypes.put(alias, type);
	}

	protected EntityType getEntityType(String attr) {
		return (EntityType) entityTypes.get(alias);
	}

	public Class getEntityClass() {
		return ((EntityType) entityTypes.get(alias)).getEntityClass();
	}

	public String getEntityName() {
		return ((EntityType) entityTypes.get(alias)).getEntityName();
	}

	public void setEntityClass(Class entityClass) {
		((EntityType) entityTypes.get(alias)).setEntityClass(entityClass);
	}

	public Object getCurrent(String attr) {
		return getCurrent();
	}

	public Object getCurrent() {
		return super.getCurrent(alias);
	}
	
	protected String getEntityName(String attr) {
		return getEntityName();
	}

	public String processAttr(String attr) {
		return attr;
	}

	public void setCurrent(Object object) {
		current.put(alias, object);
	}

}
