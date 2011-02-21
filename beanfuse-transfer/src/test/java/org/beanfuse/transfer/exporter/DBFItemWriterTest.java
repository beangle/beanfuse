//$Id: DBFItemWriterTest.java 2008-7-8 下午08:45:48 chaostone Exp $
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
package org.beanfuse.transfer.exporter;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.lang.SystemUtils;
import org.beanfuse.transfer.exporter.writer.DBFItemWriter;
import org.testng.annotations.Test;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

public class DBFItemWriterTest {

	@Test
	public void testWriter() throws Exception {
		DBFItemWriter writer = new DBFItemWriter();

		String dbfile = SystemUtils.getJavaIoTmpDir() + "/test.dbf";
		FileOutputStream fos = new FileOutputStream(dbfile);

		writer.setContext(null);
		writer.setOutputStream(fos);
		writer.writeTitle(new Object[] { "code", "name" });
		writer.write(new Object[] { "001", "apple" });
		writer.write(new Object[] { "002", "banana" });
		writer.write(new Object[] { "003", "香蕉香蕉香蕉" });
		writer.write(new Object[] { "004", "long word of unknow catalog fruit" });
		writer.close();
		fos.close();

		InputStream inputStream = new FileInputStream(dbfile);
		DBFReader reader = new DBFReader(inputStream);
		reader.setCharactersetName("gbk");
		int numberOfFields = reader.getFieldCount();

		assertEquals(2, numberOfFields);
		DBFField field0 = reader.getField(0);
		assertEquals("code", field0.getName());
		DBFField field1 = reader.getField(1);
		assertEquals("name", field1.getName());
		Object[] rowObjects;
		while ((rowObjects = reader.nextRecord()) != null) {

			for (int i = 0; i < rowObjects.length; i++) {

				System.out.println(rowObjects[i]);
			}
		}
		inputStream.close();

	}
}
