//$Id: SeqStyle.java 2008-8-4 上午10:42:26 asus Exp $
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
 *chaostone             2008-8-4         Created
 *  
 ********************************************************************************/

package org.beanfuse.utils.multilevelseq;

public interface SeqNumStyle {
	/** 中文数字 */
	public static final SeqNumStyle HANZI = new HanZiSeqStyle();

	/** 数字 */
	public static final SeqNumStyle ARABIC = new ArabicSeqStyle();

	public String build(int seq);
}
