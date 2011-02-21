package org.beanfuse.db.replication;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.beanfuse.db.dialect.Dialect;
import org.beanfuse.db.replication.impl.DatabaseReplicator;
import org.beanfuse.db.replication.impl.DefaultTableFilter;
import org.beanfuse.db.replication.wrappers.DatabaseWrapper;
import org.beanfuse.db.util.DataSourceUtil;

public class ReplicatorMain {

	public static void main(String[] args) throws Exception {

		final Properties props = new Properties();
		try {
			InputStream is = DataSourceUtil.class.getResourceAsStream("/replication.properties");
			if (null == is) {
				throw new RuntimeException("cannot find replication.properties");
			}
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException("cannot find database.properties");
		}

		DatabaseWrapper source = new DatabaseWrapper(props.getProperty("source.schema"));
		source.connect(DataSourceUtil.getDataSource("source"), (Dialect) (Class.forName(props
				.getProperty("source.dialect")).newInstance()));

		DatabaseWrapper target = new DatabaseWrapper(props.getProperty("target.schema"));
		target.connect(DataSourceUtil.getDataSource("target"), (Dialect) (Class.forName(props
				.getProperty("target.dialect")).newInstance()));

		Replicator replicator = new DatabaseReplicator(source, target);

		Set<String> tables = (Set) source.getMetadata().getTables().keySet();
		DefaultTableFilter filter = new DefaultTableFilter();
		filter.addExclude("PUBLIC.DUAL");
		filter.addInclude("SUES_NEW.PYJH_T");
		replicator.addTables(filter.filter(tables));
		replicator.start();
	}
}
