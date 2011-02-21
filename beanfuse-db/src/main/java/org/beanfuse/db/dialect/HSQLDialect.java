package org.beanfuse.db.dialect;

import java.sql.Types;

public class HSQLDialect extends AbstractDialect {
	public HSQLDialect() {
		super();
		ss=new SequenceSupport();
		ss.setQuerySequenceSql("select sequence_name from information_schema.system_sequences");
		ss.setNextValSql("call next value for {}");
		ss.setSelectNextValSql("next value for {}");
		
		registerColumnType( Types.BOOLEAN, "boolean" );
		registerColumnType( Types.BIGINT, "bigint" );
		registerColumnType( Types.BINARY, "binary" );
		registerColumnType( Types.BIT, "bit" );
		registerColumnType( Types.CHAR, "char(1)" );
		registerColumnType( Types.DATE, "date" );
		registerColumnType( Types.DECIMAL, "decimal" );
		registerColumnType( Types.DOUBLE, "double" );
		registerColumnType( Types.FLOAT, "float" );
		registerColumnType( Types.INTEGER, "integer" );
		registerColumnType( Types.LONGVARBINARY, "longvarbinary" );
		registerColumnType( Types.LONGVARCHAR, "longvarchar" );
		registerColumnType( Types.SMALLINT, "smallint" );
		registerColumnType( Types.TINYINT, "tinyint" );
		registerColumnType( Types.TIME, "time" );
		registerColumnType( Types.TIMESTAMP, "timestamp" );
		registerColumnType( Types.VARCHAR, "varchar($l)" );
		registerColumnType( Types.VARBINARY, "varbinary($l)" );
		registerColumnType( Types.NUMERIC, "numeric" );
		//HSQL has no Blob/Clob support .... but just put these here for now!
		registerColumnType( Types.BLOB, "longvarbinary" );
		registerColumnType( Types.CLOB, "longvarchar" );
	}
	
	public String getLimitString(String sql, boolean hasOffset) {
		return new StringBuffer( sql.length() + 10 )
				.append( sql )
				.insert( sql.toLowerCase().indexOf( "select" ) + 6, hasOffset ? " limit ? ?" : " top ?" )
				.toString();
	}

}
