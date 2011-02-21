package org.beanfuse.db.dialect;

public interface Dialect {

	public SequenceSupport getSequenceSupport();

	String getTypeName(int typecode, int size, int precision, int scale);

	String getTypeName(int typecode);

	String getCreateTableString();

	String getLimitString(String sql, boolean hasOffset);

}
