//$Id: NumSeqStye.java 2008-8-4 上午10:49:46 asus Exp $
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

public class ArabicSeqStyle implements SeqNumStyle {
	public String build(int seq) {
		return String.valueOf(seq);
	}
}
