//$Id: DefaultExcelWriter.java,v 1.1 2007-3-24 下午09:06:19 chaostone Exp $
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
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beanfuse.transfer.Transfer;
import org.beanfuse.transfer.exporter.Context;

public class ExcelItemWriter extends AbstractItemWriter {
	protected int countPerSheet = 50000;

	protected HSSFWorkbook workbook = new HSSFWorkbook(); // 建立新HSSFWorkbook对象

	protected int index = 0;

	protected HSSFSheet sheet = workbook.createSheet((index + 1) + "-" + countPerSheet);

	protected HSSFCellStyle dateStyle = workbook.createCellStyle();

	protected HSSFCellStyle timeStyle = workbook.createCellStyle();

	public ExcelItemWriter() {
		super();
		dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		timeStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
	}

	public ExcelItemWriter(OutputStream outputStream) {
		this();
		this.outputStream = outputStream;
	}

	public int getCountPerSheet() {
		return countPerSheet;
	}

	public void setCountPerSheet(int dataNumPerSheet) {
		this.countPerSheet = dataNumPerSheet;
	}

	public void write(Object obj) {
		if (index + 1 >= countPerSheet) {
			sheet = workbook.createSheet((index + 1) + "-" + (index + countPerSheet));
		}
		writeExcel(obj);
		index++;
	}

	public void writeTitle(Object title) {
		write(title);
	}

	public String getFormat() {
		return Transfer.EXCEL;
	}

	public void writeExcel(Object datas) {
		HSSFRow row = sheet.createRow(index); // 建立新行
		if (datas != null) {
			if (datas.getClass().isArray()) {
				Object[] values = (Object[]) datas;
				for (int i = 0; i < values.length; i++) {
					HSSFCell cell = row.createCell((short) i);
					// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					if (values[i] instanceof Number) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(((Number) values[i]).doubleValue());
					} else if (values[i] instanceof java.sql.Date) {
						cell.setCellValue((Date) values[i]);
						cell.setCellStyle(dateStyle);
					} else if (values[i] instanceof java.util.Date) {
						cell.setCellValue((Date) values[i]);
						cell.setCellStyle(timeStyle);
					} else if (values[i] instanceof Calendar) {
						cell.setCellValue((Calendar) values[i]);
						cell.setCellStyle(timeStyle);
					} else {
						cell.setCellValue(new HSSFRichTextString((values[i] == null) ? ""
								: values[i].toString()));
					}
				}
			} else {
				HSSFCell cell = row.createCell((short) 0);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				if (datas instanceof Number) {
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}
				cell.setCellValue(new HSSFRichTextString(datas.toString()));
			}
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
	 * 设置每个sheet多少条记录
	 */
	public void setContext(Context context) {
		super.setContext(context);
		if (null != context) {
			Object count = context.getDatas().get("countPerSheet");
			if (null != count && NumberUtils.isNumber(count.toString())) {
				int countParam = NumberUtils.toInt(count.toString());
				if (countParam > 0)
					this.countPerSheet = countParam;
			}
		}
	}
}
