package org.beanfuse.db.replication;

import org.beanfuse.db.dialect.HSQLDialect;
import org.beanfuse.db.dialect.OracleDialect;
import org.beanfuse.db.replication.impl.DatabaseReplicator;
import org.beanfuse.db.replication.wrappers.DatabaseWrapper;
import org.beanfuse.db.util.DataSourceUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReplicatorTest {

	DatabaseWrapper source;

	@BeforeClass
	private void init() {
		source = new DatabaseWrapper("PUBLIC");
		source.connect(DataSourceUtil.getDataSource("hsqldb"), new HSQLDialect());
	}

	@Test(dataProvider = "tables")
	public void hsqlReplication(String table) {
		DatabaseWrapper target = new DatabaseWrapper("PUBLIC");
		target.connect(DataSourceUtil.getDataSource("hsqldb_target"), new HSQLDialect());

		Replicator replicator = new DatabaseReplicator(source, target);
		replicator.addTable(table);
		replicator.start();
	}

	public void testOracleReplication(String table) {
		DatabaseWrapper target = new DatabaseWrapper("test");
		target.connect(DataSourceUtil.getDataSource("oracle"), new OracleDialect());

		Replicator replicator = new DatabaseReplicator(source, target);
		replicator.addTable(table);
		replicator.start();
	}

	@DataProvider
	private String[][] tables() {
		return new String[][] { { "xtqx_zy_t" }, { "xtqx_zy_lb_t" }, { "xtqx_yh_t" },
				{ "xtqx_js_t" } };
	}
}
