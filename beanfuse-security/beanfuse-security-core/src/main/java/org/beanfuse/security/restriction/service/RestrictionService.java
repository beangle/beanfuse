//$Id: RestrictionService.java,v 1.1 2007-10-14 下午04:41:01 chaostone Exp $
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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone              2007-10-14         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.restriction.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.dao.AuthorityDao;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.Restriction;
import org.beanfuse.security.restriction.Pattern;

/**
 * 资源访问约束服务
 * 
 * @author chaostone
 * 
 */
public interface RestrictionService {

	public List getRestrictions(User user, Resource resource);

	public List getValues(Param param);

	/**
	 * 从总的集合中找出item中规定的集合
	 * 
	 * @param values
	 * @param item
	 * @return
	 */
	public Set select(Collection values, Restriction res, Param param);

	/**
	 * 从总的集合中找出items中规定的集合的并集
	 * 
	 * @param values
	 * @param items
	 * @return
	 */
	public Set select(Collection values, List restrictions, Param param);

	public void setAuthorityDao(AuthorityDao authorityDao);

	public void apply(EntityQuery query, Collection patterns, Collection restrictions);

	public void apply(EntityQuery query, Pattern pattern, Restriction restriction);

	public void setRestrictionApply(RestrictionApply apply);

}
