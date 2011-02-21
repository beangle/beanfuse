package org.beanfuse.persist.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.Types;

/**
 * 纠正了原有Hibernate对schema的使用错误
 * 
 * @author chaostone
 * 
 */
public class HSQLDialect extends org.hibernate.dialect.HSQLDialect {

	public HSQLDialect() {
		super();
		registerColumnType(Types.BOOLEAN, "bit");
	}

	public String[] getCreateSequenceStrings(String sequenceName, int initialValue,
			int incrementSize) {
		return new String[] {
				"create table " + renameSequenceName(sequenceName) + " (zero integer)",
				"insert into " + renameSequenceName(sequenceName) + " values (0)",
				"create sequence " + sequenceName + " start with " + initialValue
						+ " increment by " + incrementSize };
	}

	public String[] getDropSequenceStrings(String sequenceName) {
		return new String[] { "drop table " + renameSequenceName(sequenceName) + " if exists",
				"drop sequence " + sequenceName };
	}

	private static String renameSequenceName(String sequenceName) {
		if (sequenceName.indexOf('.') < 0) {
			return "dual_" + sequenceName;
		} else {
			return StringUtils.replace(sequenceName, ".", ".dual_");
		}
	}

}
