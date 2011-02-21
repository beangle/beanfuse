package org.beanfuse.db.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.db.dialect.Dialect;
import org.beanfuse.db.dialect.SequenceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDBC database metadata
 * 
 * @author chaostone
 */
public class DatabaseMetadata {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseMetadata.class);

	private final Map<String, TableMetadata> tables = new CaseInsensitiveMap();
	private final Set sequences = new HashSet();
	private final boolean extras;

	private DatabaseMetaData meta;

	private Dialect dialect;

	public DatabaseMetadata(Connection connection, Dialect dialect) {
		this(connection, dialect, false);
	}

	public DatabaseMetadata(Connection connection, Dialect dialect, boolean extras) {
		try {
			meta = connection.getMetaData();
			this.extras = extras;
			this.dialect = dialect;
			initSequences(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static final String[] TYPES = { "TABLE", "VIEW" };

	public TableMetadata getTableMetadata(String qualifiedName) {
		return getTableMetadata(qualifiedName, null, null);
	}

	public TableMetadata getTableMetadata(String name, String schema, String catalog) {
		String identifier = TableMetadata.qualify(catalog, schema, name);
		TableMetadata table = (TableMetadata) tables.get(identifier);
		if (table != null) {
			return table;
		} else {
			try {
				ResultSet rs = null;
				try {
					if (meta.storesUpperCaseQuotedIdentifiers()
							&& meta.storesUpperCaseIdentifiers()) {
						rs = meta.getTables(StringUtils.upperCase(catalog), StringUtils
								.upperCase(schema), StringUtils.upperCase(name), TYPES);
					} else if (meta.storesLowerCaseQuotedIdentifiers()
							&& meta.storesLowerCaseIdentifiers()) {
						rs = meta.getTables(StringUtils.lowerCase(catalog), StringUtils
								.lowerCase(schema), StringUtils.lowerCase(name), TYPES);
					} else {
						rs = meta.getTables(catalog, schema, name, TYPES);
					}

					while (rs.next()) {
						String tableName = rs.getString("TABLE_NAME");
						if (name.equalsIgnoreCase(tableName)) {
							table = new TableMetadata(rs, meta, extras);
							tables.put(identifier, table);
							return table;
						}
					}
					logger.info("table not found: " + name);
					return null;

				} finally {
					if (rs != null)
						rs.close();
				}
			} catch (SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		}

	}

	public void loadAllMetadata(String schema, String catalog, boolean isQuoted) {
		try {
			ResultSet rs = null;
			try {
				if (meta.storesUpperCaseQuotedIdentifiers() && meta.storesUpperCaseIdentifiers()) {
					rs = meta.getTables(StringUtils.upperCase(catalog), StringUtils
							.upperCase(schema), null, TYPES);
				} else if (meta.storesLowerCaseQuotedIdentifiers()
						&& meta.storesLowerCaseIdentifiers()) {
					rs = meta.getTables(StringUtils.lowerCase(catalog), StringUtils
							.lowerCase(schema), null, TYPES);
				} else {
					rs = meta.getTables(catalog, schema, null, TYPES);
				}

				while (rs.next()) {
					TableMetadata table = new TableMetadata(rs, meta, extras);
					tables.put(table.identifier(), table);
				}
			} finally {
				if (rs != null)
					rs.close();
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

	private void initSequences(Connection connection) throws SQLException {
		SequenceSupport ss = dialect.getSequenceSupport();
		if (null == ss)
			return;
		String sql = ss.getQuerySequenceSql();
		if (sql != null) {
			Statement statement = null;
			ResultSet rs = null;
			try {
				statement = connection.createStatement();
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					sequences.add(new SequenceMetadata(rs.getString(1).toLowerCase().trim()));
				}
			} finally {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			}
		}
	}

	public String toString() {
		return "DatabaseMetadata" + tables.keySet().toString() + sequences.toString();
	}

	public Map<String, TableMetadata> getTables() {
		return tables;
	}

	public Set getSequences() {
		return sequences;
	}

	public Dialect getDialect() {
		return dialect;
	}

}
