//$Id: ExcelReader.java,v 1.1 2007-3-24 下午12:30:52 chaostone Exp $
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

package org.beanfuse.transfer.importer.reader;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.beanfuse.transfer.Transfer;

/**
 * Excel的每行一条数据的读取器
 * 
 * @author chaostone
 * 
 */
public class ExcelItemReader implements ItemReader {

	public static Logger logger = LoggerFactory.getLogger(ExcelItemReader.class);

	/** 标题缺省所在行 */
	public static int DEFAULT_HEADINDEX = 0;

	public static NumberFormat numberFormat;

	static {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);
	}
	public static final int sheetNum = 0;

	int headIndex;
	/**
	 * 下一个要读取的位置 标题行和代码行分别默认占据0,1
	 */
	int indexInSheet;

	/**
	 * 属性的个数，0表示在读取值的是否不做读限制
	 */
	int attrCount = 0;

	/**
	 * 读取的工作表
	 */
	HSSFWorkbook workbook;

	public ExcelItemReader() {
		super();
	}

	public ExcelItemReader(InputStream is) {
		try {
			this.workbook = new HSSFWorkbook(is);
			this.headIndex = DEFAULT_HEADINDEX;
			this.indexInSheet = headIndex + 1;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public ExcelItemReader(HSSFWorkbook workbook, int headIndex) {
		super();
		this.workbook = workbook;
		this.headIndex = headIndex;
		this.indexInSheet = headIndex + 1;
	}

	public void setWorkbook(HSSFWorkbook wb) {
		this.workbook = wb;
	}

	/**
	 * 描述放在第一行
	 */
	public String[] readDescription() {
		HSSFSheet sheet = workbook.getSheetAt(0);
		return readLine(sheet, 0);
	}

	public String[] readTitle() {
		HSSFSheet sheet = workbook.getSheetAt(0);
		String[] attrs = readLine(sheet, headIndex);
		attrCount = attrs.length;
		return attrs;
	}

	/**
	 * 遇到空白单元格停止的读行操作
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @return
	 */
	protected String[] readLine(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		if (logger.isDebugEnabled()) {
			logger.debug("values count:{}" + row.getLastCellNum());
		}
		List attrList = new ArrayList();
		for (short i = 0; i < row.getLastCellNum(); i++) {
			HSSFCell cell = row.getCell(i);
			if (null != cell) {
				String attr = cell.getRichStringCellValue().getString();
				if (StringUtils.isEmpty(attr)) {
					break;
				} else {
					attrList.add(attr.trim());
				}
			} else {
				break;
			}
		}
		String[] attrs = new String[attrList.size()];
		attrList.toArray(attrs);
		logger.debug("has attrs {}", attrs);
		return attrs;
	}

	public Object read() {
		HSSFSheet sheet = workbook.getSheetAt(sheetNum);
		if (indexInSheet > sheet.getLastRowNum()) {
			return null;
		}
		HSSFRow row = sheet.getRow(indexInSheet);
		indexInSheet++;
		// 如果是个空行,返回空记录
		if (row == null) {
			return new Object[attrCount];
		} else {
			Object[] values = new Object[((attrCount != 0) ? attrCount : row.getLastCellNum())];
			for (short k = 0; k < values.length; k++) {
				String celValue = getCelValue(row.getCell(k));
				if (null != celValue) {
					celValue = celValue.trim();
				}
				values[k] = celValue;
			}
			return values;
		}
	}

	/**
	 * @see 取cell单元格中的数据
	 * @param cell
	 * @param objClass
	 * @return
	 */
	private String getCelValue(HSSFCell cell) {
		if ((cell == null) || (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK))
			return "";
		if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			return cell.getRichStringCellValue().getString();
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return numberFormat.format(cell.getNumericCellValue());
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			if (cell.getBooleanCellValue())
				return "true";
			else
				return "false";
		} else {
			return "";
		}
	}

	public String getFormat() {
		return Transfer.EXCEL;
	}

	public int getHeadIndex() {
		return headIndex;
	}

	public void setHeadIndex(int headIndex) {
		this.headIndex = headIndex;
	}

}
