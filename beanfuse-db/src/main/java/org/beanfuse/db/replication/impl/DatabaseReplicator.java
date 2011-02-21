package org.beanfuse.db.replication.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.collection.page.PagedList;
import org.beanfuse.db.meta.TableMetadata;
import org.beanfuse.db.replication.DataWrapper;
import org.beanfuse.db.replication.Replicator;
import org.beanfuse.db.replication.wrappers.DatabaseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseReplicator implements Replicator {

	private static Logger logger = LoggerFactory.getLogger(DatabaseReplicator.class);

	List tables = new ArrayList();
	DatabaseWrapper source;
	DatabaseWrapper target;

	public DatabaseReplicator() {
		super();
	}

	public DatabaseReplicator(DatabaseWrapper source, DatabaseWrapper target) {
		super();
		this.source = source;
		this.target = target;
	}

	public boolean addTable(String table) {
		String newTable = table.toUpperCase();
		if (!StringUtils.contains(table, '.') && null != source.getSchema()) {
			newTable = source.getSchema() + "." + newTable;
		}
		TableMetadata tm = source.getMetadata().getTableMetadata(newTable);
		if (null == tm) {
			logger.error("cannot find metadata for {}", newTable);
		} else {
			tables.add(tm);
		}
		return tm != null;
	}

	public boolean addTables(Collection tables) {
		boolean success = true;
		for (Iterator iter = tables.iterator(); iter.hasNext();) {
			success &= addTable((String) iter.next());
		}
		return success;
	}

	public boolean addTables(String[] tables) {
		boolean success = true;
		for (int i = 0; i < tables.length; i++) {
			success &= addTable(tables[i]);
		}
		return success;
	}

	public void reset() {

	}

	public void setSource(DataWrapper source) {
		this.source = (DatabaseWrapper) source;
	}

	public void setTarget(DataWrapper target) {
		this.target = (DatabaseWrapper) target;
	}

	public void start() {
		for (Iterator iter = tables.iterator(); iter.hasNext();) {
			TableMetadata table = (TableMetadata) iter.next();
			try {
				String sourceSchema = source.getSchema();
				int count = source.count(table);
				int curr = 0;
				PageLimit limit = new PageLimit(1, 5000);
				while (curr < count) {
					table.setSchema(sourceSchema);
					List data = source.getData(table, limit);
					table.setSchema(target.getSchema());
					target.pushData(table, data);
					curr += data.size();
					logger.info("replicate {} data {}", table, data.size());
				}
				table.setSchema(sourceSchema);
			} catch (Exception e) {
				logger.error("replicate  " + table.identifier(), e);
			}
		}
	}

}
