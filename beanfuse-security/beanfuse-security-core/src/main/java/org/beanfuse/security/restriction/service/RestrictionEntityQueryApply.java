//$Id: RestrictionEntityQueryApply.java 2008-6-9 下午09:38:33 chaostone Exp $
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
package org.beanfuse.security.restriction.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.AuthorityException;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.Restriction;
import org.beanfuse.security.restriction.Pattern;

public class RestrictionEntityQueryApply implements RestrictionApply {

	public void apply(EntityQuery query, Collection patterns, Collection restrictions) {
		String prefix = "(";
		StringBuilder conBuffer = new StringBuilder();
		List paramValues = new ArrayList();
		int index = 0;
		Map patternMap = new HashMap();
		for (Iterator iterator = patterns.iterator(); iterator.hasNext();) {
			Pattern pattern = (Pattern) iterator.next();
			patternMap.put(pattern.getParamGroup(), pattern);
		}

		for (Iterator iterator = restrictions.iterator(); iterator.hasNext();) {
			Restriction restriction = (Restriction) iterator.next();

			// 处理限制对应的模式
			Pattern pattern = (Pattern) patternMap.get(restriction
					.getParamGroup());
			if (null == pattern || StringUtils.isEmpty(pattern.getContent())) {
				continue;
			}
			String patternContent = pattern.getContent();
			patternContent = StringUtils.replace(patternContent, "{alias}", query.getAlias());
			String[] contents = StringUtils.split(
					StringUtils.replace(patternContent, " and ", "$"), "$");

			StringBuilder singleConBuf = new StringBuilder(prefix);
			for (int i = 0; i < contents.length; i++) {
				String content = contents[i];
				Condition c = new Condition(content);
				List params = c.getNamedParams();
				for (Iterator iter = params.iterator(); iter.hasNext();) {
					String paramName = (String) iter.next();
					Param param = pattern.getParamGroup().getParam(paramName);
					String value = restriction.getItem(param);
					if (StringUtils.isNotEmpty(value)) {
						if (value.equals(Restriction.ALL)) {
							content = "";
						} else {
							content = StringUtils.replace(content, ":" + param.getName(), ":"
									+ param.getName() + index);
							paramValues.add(restriction.getValue(param));
						}
					} else {
						throw new AuthorityException(paramName + " had not been initialized");
					}
				}
				if (singleConBuf.length() > 1 && content.length() > 0) {
					singleConBuf.append(" and ");
				}
				singleConBuf.append(content);
			}

			if (singleConBuf.length() > 1) {
				singleConBuf.append(')');
				if (conBuffer.length() > 0) {
					conBuffer.append(" or ");
				}
				conBuffer.append(singleConBuf.toString());
			}
			index++;
		}
		if (conBuffer.length() > 0) {
			Condition con = new Condition(conBuffer.toString());
			con.setValues(paramValues);
			query.add(con);
		}
	}

	public void apply(EntityQuery query, Pattern pattern, Restriction restriction) {
		apply(query, Collections.singletonList(pattern), Collections.singletonList(restriction));
	}

}
