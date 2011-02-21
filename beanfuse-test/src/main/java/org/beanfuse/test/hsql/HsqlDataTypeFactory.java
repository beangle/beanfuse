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
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-10-29            Created
 *  
 ********************************************************************************/
package org.beanfuse.test.hsql;

import java.sql.Types;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HsqlDataTypeFactory extends DefaultDataTypeFactory {

	private static final Logger logger = LoggerFactory.getLogger(HsqlDataTypeFactory.class);

	/**
	 * since jdk 1.4
	 */
	public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
		 if (sqlType == Types.BOOLEAN) {
		 return DataType.BOOLEAN;
		 }
		return super.createDataType(sqlType, sqlTypeName);
	}

}
