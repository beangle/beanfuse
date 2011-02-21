//$Id: DBFWriter.java 2008-7-8 下午08:14:11 chaostone Exp $
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
 * ============   ============  ============
 * chaostone      2008-7-8  Created
 *  
 ********************************************************************************/
package org.beanfuse.transfer.exporter.writer;

import org.beanfuse.transfer.Transfer;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;

public class DBFItemWriter extends AbstractItemWriter {
	DBFWriter writer;

	String charSet = "gbk";

	public DBFItemWriter() {
		super();
		writer = new DBFWriter();
		writer.setCharactersetName(charSet);
	}

	public void write(Object obj) {
		try {
			writer.addRecord((Object[]) obj);
		} catch (DBFException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void writeTitle(Object title) {
		Object[] attrs = (Object[]) title;
		try {
			DBFField fields[] = new DBFField[attrs.length];
			for (int i = 0; i < fields.length; i++) {
				fields[i] = new DBFField();
				fields[i].setName((String) attrs[i]);
				fields[i].setDataType(DBFField.FIELD_TYPE_C);
				// FIXME
				fields[i].setFieldLength(255);
			}
			writer.setFields(fields);
		} catch (DBFException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void close() {
		try {
			writer.write(outputStream);
		} catch (DBFException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String getFormat() {
		return Transfer.DBF;
	}

}
