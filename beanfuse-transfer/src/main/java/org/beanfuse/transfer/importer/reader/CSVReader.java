//$Id: CSVReader.java,v 1.1 2007-3-24 下午12:11:52 chaostone Exp $
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

import java.io.IOException;
import java.io.LineNumberReader;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.beanfuse.transfer.Transfer;

public class CSVReader implements ItemReader {
	/**
	 * Commons Logging instance.
	 */
	protected static Logger logger = LoggerFactory.getLogger(CSVReader.class);

	LineNumberReader reader;

	public CSVReader(LineNumberReader reader) {
		this.reader = reader;
	}

	public String[] readDescription() {
		return null;
	}

	public String[] readTitle() {
		try {
			reader.readLine();
			return StringUtils.split(reader.readLine(), ",");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Object read() {
		String curData = null;
		try {
			curData = reader.readLine();
		} catch (IOException e1) {
			logger.error(curData, e1);
		}
		if (null == curData) {
			return null;
		} else {
			return StringUtils.split(curData, ",");
		}
	}

	public String getFormat() {
		return Transfer.CSV;
	}
}
