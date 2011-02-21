//$Id: AbstractTemplateExporter.java,v 1.1 2007-3-24 下午09:25:24 chaostone Exp $
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
 *chaostone      2007-3-24         Created
 *  
 ********************************************************************************/

package org.beanfuse.transfer.exporter;

import java.util.Locale;

import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.TransferListener;
import org.beanfuse.transfer.TransferResult;
import org.beanfuse.transfer.exporter.writer.TemplateWriter;
import org.beanfuse.transfer.exporter.writer.Writer;

public class TemplateExporter implements Exporter {
	/**
	 * 数据读取对象
	 */
	protected TemplateWriter writer;

	/**
	 * 设置数据访问上下文
	 */
	public void setContext(Context context) {
		writer.setContext(context);
	}

	/**
	 * not supported now
	 */
	public Transfer addListener(TransferListener listener) {
		return this;
	}

	public Object getCurrent() {
		return null;
	}

	public String getDataName() {
		return null;
	}

	public int getFail() {
		return 0;
	}

	public String getFormat() {
		return writer.getFormat();
	}

	public Locale getLocale() {
		return null;
	}

	public int getSuccess() {
		return 0;
	}

	public int getTranferIndex() {
		return 0;
	}

	public void transfer(TransferResult tr) {
		writer.write();
		writer.close();
	}

	public void transferItem() {

	}

	public void setWriter(Writer writer) {
		if (writer instanceof TemplateWriter) {
			this.writer = (TemplateWriter) writer;
		}
	}
}
