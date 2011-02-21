package org.beanfuse.db.meta;

import static java.sql.Types.BIGINT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeUtils {

	private static Logger logger = LoggerFactory.getLogger(TypeUtils.class);

	public static void setValue(PreparedStatement ps, int index, Object value, int sqlType)
			throws SQLException {
		if (null == value) {
			ps.setNull(index, sqlType);
			return;
		}
		switch (sqlType) {
		case BIGINT:
			ps.setLong(index, (Long) value);
			break;
		case INTEGER:
			ps.setInt(index, (Integer) value);
			break;
		case VARCHAR:
			ps.setString(index, (String) value);
			break;
		case Types.DATE:
			ps.setDate(index, (Date) value);
			break;
		case Types.TIMESTAMP:
			ps.setTimestamp(index, (Timestamp) value);
			break;
		case Types.BOOLEAN:
			ps.setBoolean(index, (Boolean) value);
			break;
		case Types.FLOAT:
		case Types.DECIMAL:
			ps.setBigDecimal(index, (BigDecimal) value);
			break;
		default:
			logger.warn("unsupported type {}", sqlType);
		}

	}
}
