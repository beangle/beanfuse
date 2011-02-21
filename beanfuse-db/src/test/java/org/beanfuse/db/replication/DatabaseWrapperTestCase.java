package org.beanfuse.db.replication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseWrapperTestCase {
	protected DataWrapper dataWrapper;
	protected static Logger log = LoggerFactory.getLogger(DatabaseWrapperTestCase.class.getName());

	protected void listTables() {
		// log.info("tables {}", dataWrapper.getTableNames());
		// log.info("sequences {}", dataWrapper.getSequenceNames());
		// log.info("views {}", dataWrapper.getViewNames());
		dataWrapper.close();
	}

	public void testA() {
	}

//	public void DBFDataWrapper() {
//		String dbfile = DatabaseWrapperTestCase.class.getResource("/test.dbf").getPath();
//		DBFDataWrapper source = new DBFDataWrapper();
//		source.setDataFile(new File(dbfile));
//		List tables = source.getTableNames();
//		List sequences = source.getSequenceNames();
//		List views = source.getViewNames();
//		source.close();
//	}
}
