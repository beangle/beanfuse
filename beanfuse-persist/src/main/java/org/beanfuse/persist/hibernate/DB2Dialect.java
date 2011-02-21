package org.beanfuse.persist.hibernate;

/**
 * 支持DB2PooledSequences的Dialect
 * 
 * @author chaostone
 * 
 */
public class DB2Dialect extends org.hibernate.dialect.DB2Dialect {

	public boolean supportsPooledSequences() {
		return true;
	}

}
