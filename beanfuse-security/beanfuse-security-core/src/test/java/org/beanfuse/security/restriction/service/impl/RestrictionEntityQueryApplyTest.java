//$Id: RestrictionEntityQueryApplyTest.java 2008-6-9 下午09:40:13 chaostone Exp $
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
 * chaostone      2008-6-9  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.restriction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.User;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.ParamGroup;
import org.beanfuse.security.restriction.Pattern;
import org.beanfuse.security.restriction.Restriction;
import org.beanfuse.security.restriction.service.RestrictionEntityQueryApply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class RestrictionEntityQueryApplyTest  {

	Logger logger = LoggerFactory.getLogger(RestrictionEntityQueryApplyTest.class);

	@Test
	public void testApply() {
		// pattern 1
		Pattern pattern = new org.beanfuse.security.restriction.model.Pattern();
		pattern.setContent("stdType.id in(:stdTypeIds) and {alias}.department.id in(:departIds)");
		ParamGroup group = new org.beanfuse.security.restriction.model.ParamGroup();
		Param param1 = new org.beanfuse.security.restriction.model.Param();
		param1.setName("stdTypeIds");
		param1.setType("java.lang.String");
		param1.setMultiValue(true);
		param1.setId(new Long(1));
		Param param2 = new org.beanfuse.security.restriction.model.Param();
		param2.setName("departIds");
		param2.setType("java.lang.String");
		param2.setMultiValue(true);
		param2.setId(new Long(2));
		group.getParams().add(param1);
		group.getParams().add(param2);
		pattern.setParamGroup(group);

		// pattern 2
		Pattern pattern2 = new org.beanfuse.security.restriction.model.Pattern();
		pattern2.setContent("major.id in(:majorIds) and {alias}.grade in(:grades)");
		ParamGroup group2 = new org.beanfuse.security.restriction.model.ParamGroup();
		Param param3 = new org.beanfuse.security.restriction.model.Param();
		param3.setName("majorIds");
		param3.setType("java.lang.String");
		param3.setMultiValue(true);
		param3.setId(new Long(3));
		Param param4 = new org.beanfuse.security.restriction.model.Param();
		param4.setName("grades");
		param4.setType("java.lang.String");
		param4.setMultiValue(true);
		param4.setId(new Long(4));
		group2.getParams().add(param3);
		group2.getParams().add(param4);
		pattern2.setParamGroup(group2);

		// restriction 1
		Restriction restriction = new org.beanfuse.security.restriction.model.Restriction();
		restriction.setParamGroup(group);
		restriction.setItem(param1, "1,3,2");
		restriction.setItem(param2, "1,3,1");

		RestrictionEntityQueryApply apply = new RestrictionEntityQueryApply();
		EntityQuery query = new EntityQuery(User.class, "user");
		apply.apply(query, pattern, restriction);

		Restriction restriction2 = new org.beanfuse.security.restriction.model.Restriction();
		restriction2.setParamGroup(group2);
		restriction2.setItem(param3, "1,3,2");
		restriction2.setItem(param4, "*");

		EntityQuery query2 = new EntityQuery(User.class, "user");
		List restristions = new ArrayList();
		restristions.add(restriction);
		restristions.add(restriction2);

		List patterns = new ArrayList();
		patterns.add(pattern);
		patterns.add(pattern2);
		apply.apply(query2, patterns, restristions);
		logger.info(query2.toQueryString());
	}
}
