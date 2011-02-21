package org.beanfuse.db.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import org.beanfuse.db.dialect.Dialect;

/**
 * JDBC column metadata
 * 
 * @author chaostone
 */
public class ColumnMetadata implements Comparable {
	private String name;
	private String typeName;
	private int columnSize;
	private int decimalDigits;
	private String isNullable;
	private int typeCode;

	public ColumnMetadata() {
		super();
	}

	public ColumnMetadata(ResultSet rs) throws SQLException {
		name = rs.getString("COLUMN_NAME");
		columnSize = rs.getInt("COLUMN_SIZE");
		decimalDigits = rs.getInt("DECIMAL_DIGITS");
		isNullable = rs.getString("IS_NULLABLE");
		typeCode = rs.getInt("DATA_TYPE");
		typeName = new StringTokenizer(rs.getString("TYPE_NAME"), "() ").nextToken();
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public String getNullable() {
		return isNullable;
	}

	public String toString() {
		return "ColumnMetadata(" + name + ')';
	}

	public int getTypeCode() {
		return typeCode;
	}

	public String getSqlType(Dialect dialect) {
		return dialect.getTypeName(typeCode, columnSize, columnSize, decimalDigits);
	}

	public int compareTo(Object o) {
		ColumnMetadata other = (ColumnMetadata) o;
		return getName().compareToIgnoreCase(other.getName());
	}

}
