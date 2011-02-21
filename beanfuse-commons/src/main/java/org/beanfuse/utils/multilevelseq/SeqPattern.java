//$Id: Style.java 2008-8-4 上午10:42:49 asus Exp $
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
 *chaostone      2008-8-4         Created
 *  
 ********************************************************************************/

package org.beanfuse.utils.multilevelseq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class SeqPattern {

	private MultiLevelSeqGenerator generator;

	private String pattern;

	private int level;

	private SeqNumStyle seqNumStyle;

	private int seq = 0;

	private final List params = new ArrayList();

	// 从模式中进行分析{}
	public SeqPattern(SeqNumStyle seqStyle, String pattern) {
		this.seqNumStyle = seqStyle;
		this.pattern = pattern;
		String remainder = pattern;
		while (StringUtils.isNotEmpty(remainder)) {
			String p = StringUtils.substringBetween(remainder, "{", "}");
			if (StringUtils.isEmpty(p)) {
				break;
			}
			if (NumberUtils.isDigits(p)) {
				params.add(new Integer(p));
			}
			remainder = StringUtils.substringAfter(remainder, "{" + p + "}");
		}

		Collections.sort(params);
		this.level = ((Integer) params.get(params.size() - 1)).intValue();
		params.remove(params.size() - 1);
	}

	public String curSeqText() {
		return seqNumStyle.build(seq);
	}

	public String next() {
		seq++;
		String text = pattern;
		for (Iterator iterator = params.iterator(); iterator.hasNext();) {
			Integer paramLevel = (Integer) iterator.next();
			text = StringUtils.replace(text, "{" + paramLevel + "}", generator
					.getSytle(paramLevel.intValue()).curSeqText());
		}
		return StringUtils.replace(text, "{" + level + "}", seqNumStyle
				.build(seq));
	}

	public void reset() {
		seq = 0;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public SeqNumStyle getSeqNumStyle() {
		return seqNumStyle;
	}

	public void setSeqNumStyle(SeqNumStyle seqNumStyle) {
		this.seqNumStyle = seqNumStyle;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return this.pattern;
	}

	public MultiLevelSeqGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(MultiLevelSeqGenerator generator) {
		this.generator = generator;
	}

}
