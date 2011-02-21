package org.beanfuse.db.replication.wrappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.collection.page.SinglePage;
import org.beanfuse.db.dialect.Dialect;
import org.beanfuse.db.meta.ColumnMetadata;
import org.beanfuse.db.meta.DatabaseMetadata;
import org.beanfuse.db.meta.TableMetadata;
import org.beanfuse.db.meta.TypeUtils;
import org.beanfuse.db.replication.DataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DatabaseWrapper extends JdbcTemplate implements DataWrapper {

	protected static Logger log = LoggerFactory.getLogger(DatabaseWrapper.class.getName());

	protected DatabaseMetadata meta;
	protected String catalog;
	protected String productName;
	protected String schema;

	protected List tableNames = new ArrayList();
	protected List viewNames = new ArrayList();
	protected List sequenceNames = new ArrayList();

	public DatabaseWrapper() {
		super();
	}

	public DatabaseWrapper(String schema) {
		super();
		this.schema = schema;
	}

	public List getData(String tableName) {
		TableMetadata tableMeta = meta.getTables().get(tableName);
		if (null == tableMeta) {
			return Collections.EMPTY_LIST;
		} else {
			return getData(tableMeta);
		}
	}

	public int count(TableMetadata table) {
		String sql = getQueryString(table);
		String countStr = "select count(*) from (" + sql + ")";
		return queryForInt(countStr);
	}

	public List getData(TableMetadata table, PageLimit limit) {
		String sql = getQueryString(table);
		String limitSql = meta.getDialect().getLimitString(sql, limit.getPageNo() > 1);
		if (limit.getPageNo() == 1) {
			return query(limitSql, new Object[] { limit.getPageSize() });
		} else {
			return query(limitSql, new Object[] { limit.getPageNo(), limit.getPageSize() });
		}
	}

	public List getData(TableMetadata table) {
		return query(getQueryString(table));
	}

	private String getQueryString(TableMetadata table) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		String[] columnNames = table.getColumnNames();
		for (String columnName : columnNames) {
			sb.append(columnName).append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ").append(table.getName());
		return sb.toString();
	}

	public void pushData(final TableMetadata table, List datas) {
		if (null == meta.getTableMetadata(table.identifier())) {
			execute(table.sqlCreateString(meta.getDialect()));
		}
		final String[] columnNames = table.getColumnNames();
		String insertSql = table.sqlInsertString();
		try {
			Connection conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(insertSql);
			for (Object item : datas) {
				final Object[] data = (Object[]) item;
				for (int i = 0; i < columnNames.length; i++) {
					ColumnMetadata cm = table.getColumnMetadata(columnNames[i]);
					TypeUtils.setValue(ps, i + 1, data[i], cm.getTypeCode());
				}
				ps.execute();
			}
			ps.close();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
	}

	public List query(String sql) {
		return query(sql, (Object[]) null);
	}

	public List query(String sql, Object[] params) {
		return query(sql, params, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				int columnCount = rs.getMetaData().getColumnCount();
				if (columnCount == 1) {
					return rs.getObject(1);
				} else {
					Object[] row = new Object[columnCount];
					for (int i = 0; i < columnCount; i++) {
						row[i] = rs.getObject(i + 1);
					}
					return row;
				}
			}
		});
	}

	public void setMetadata(DatabaseMetadata metadata) {
		this.meta = metadata;
	}

	public DatabaseMetadata getMetadata() {
		return meta;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Dialect getDialect() {
		return meta.getDialect();
	}

	/**
	 * conntect to data source
	 * 
	 * @param targetDB
	 * @param dialect
	 */
	public void connect(DataSource dataSource, Dialect dialect) {
		try {
			setDataSource(dataSource);
			meta = new DatabaseMetadata(dataSource.getConnection(), dialect);
			meta.loadAllMetadata(getSchema(), null, false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
