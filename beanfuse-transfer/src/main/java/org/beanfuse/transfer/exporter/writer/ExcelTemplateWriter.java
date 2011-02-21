//$Id: ExcelTemplateWriter.java,v 1.1 2007-3-24 下午09:27:46 chaostone Exp $
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

package org.beanfuse.transfer.exporter.writer;

import java.io.OutputStream;
import java.net.URL;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.exporter.Context;

public class ExcelTemplateWriter implements TemplateWriter {

	protected URL template;

	protected XLSTransformer transformer = new XLSTransformer();

	protected Context context;

	protected OutputStream outputStream;

	protected HSSFWorkbook workbook;

	public ExcelTemplateWriter() {
		super();
	}

	public ExcelTemplateWriter(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	public String getFormat() {
		return Transfer.EXCEL;
	}

	public URL getTemplate() {
		return template;
	}

	public void setTemplate(URL template) {
		this.template = template;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void write() {
		populateContext();
		try {
			workbook = transformer.transformXLS(template.openStream(), context.getDatas());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void close() {
		try {
			workbook.write(outputStream);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * 留待重置和对模板的数据进行特殊处理
	 * 
	 */
	protected void populateContext() {
		template = (URL) context.getDatas().get(TEMPLATE_PATH);
		if (null == template) {
			throw new RuntimeException("Empty template path!");
		}
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
