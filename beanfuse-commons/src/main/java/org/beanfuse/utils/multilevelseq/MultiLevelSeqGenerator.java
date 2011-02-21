//$Id: SerializableGenerator.java 2008-8-1 下午02:30:56 Brian Exp $
/*
 *
 * Copyright c 2005-2009.
 * 
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
 *chaostone      2008-8-1         Created
 *  
 ********************************************************************************/

package org.beanfuse.utils.multilevelseq;

import java.util.HashMap;
import java.util.Map;

public class MultiLevelSeqGenerator {

	private final Map patterns = new HashMap();

	public SeqPattern getSytle(int level) {
		return (SeqPattern) patterns.get(new Integer(level));
	}

	public String next(int level) {
		return getSytle(level).next();
	}

	public void add(SeqPattern style) {
		style.setGenerator(this);
		patterns.put(new Integer(style.getLevel()), style);
	}

	public void reset(int level) {
		((SeqPattern) patterns.get(new Integer(level))).reset();
	}
}
