package org.beanfuse.db.dialect;

import java.util.Iterator;
import java.util.Map;

import org.beanfuse.db.meta.DatabaseMetadata;
import org.beanfuse.db.meta.TableMetadata;
import org.beanfuse.db.util.DataSourceUtil;
import org.testng.annotations.BeforeClass;

public class HSQLDialectTest extends DialectTestCase {
	@BeforeClass
	public void setUp() throws Exception {
		// meta = new
		// DatabaseMetadata(DataSourceUtil.getDataSource("oracle").getConnection(),
		// new OracleDialect());
		// meta.loadAllMetadata("EAMS_NEW",null,false);
		meta = new DatabaseMetadata(DataSourceUtil.getDataSource("hsqldb").getConnection(),
				new HSQLDialect());
		meta.loadAllMetadata(null, null, false);
	}

	public void testlistMetadata() {
		listMetadata();
		Map tables = meta.getTables();
		for (Iterator iter = tables.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			TableMetadata m = (TableMetadata) tables.get(name);
			log.info(m.sqlCreateString(meta.getDialect()));
		}
	}
}
