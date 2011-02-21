package org.beanfuse.persist.hibernate;

import java.io.Serializable;

import org.beanfuse.lang.StringUtil;
import org.hibernate.AssertionFailure;
import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.util.StringHelper;
import org.jvnet.inflector.Pluralizer;
import org.jvnet.inflector.lang.en.NounPluralizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An improved naming strategy that prefers embedded underscores to mixed case
 * names
 * 
 * @see DefaultNamingStrategy the default strategy
 * @author chaostone
 */
public class RailsNamingStrategy implements NamingStrategy, Serializable {

	protected static Logger logger = LoggerFactory.getLogger(RailsNamingStrategy.class);
	/**
	 * 是否对表名进行复数化
	 */
	private boolean pluralizeTableName = true;

	private Pluralizer pluralizer = new NounPluralizer();
	/**
	 * 当前表的前缀
	 */
	private String tablePrefix;
	/**
	 * A convenient singleton instance
	 */
	public static final NamingStrategy INSTANCE = new RailsNamingStrategy();

	public static String unqualify(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf('.');
		return (loc < 0) ? qualifiedName : qualifiedName.substring(loc + 1);
	}

	/**
	 * Return the unqualified class name, mixed case converted to underscores
	 */
	public String classToTableName(String className) {
		String tableName = null;
		if (pluralizeTableName) {
			tableName = pluralizer.pluralize(addUnderscores(unqualify(className)));
		} else {
			tableName = addUnderscores(unqualify(className));
		}
		tablePrefix = TableNameByModuleStrategy.getPrefix(className);
		if (null != tablePrefix) {
			tableName = tablePrefix + tableName;
		}
		if (tableName.length() > 30) {
			logger.warn("{}'s length has greate more then 30, database will not be supported!",
					tableName);
		}
		return tableName;
	}

	/**
	 * Return the full property path with underscore seperators, mixed case
	 * converted to underscores
	 */
	public String propertyToColumnName(String propertyName) {
		return addUnderscores(unqualify(propertyName));
	}

	/**
	 * Convert mixed case to underscores
	 */
	public String tableName(String tableName) {
		String newName = addUnderscores(tableName);
		if (null != tablePrefix) {
			if (!newName.startsWith(tablePrefix)) {
				newName = tablePrefix + newName;
			}
		}
		return newName;
	}

	/**
	 * Convert mixed case to underscores
	 */
	public String columnName(String columnName) {
		return addUnderscores(columnName);
	}

	protected static String addUnderscores(String name) {
		return StringUtil.unCamel(name.replace('.', '_'), '_');
	}

	public String collectionTableName(String ownerEntity, String ownerEntityTable,
			String associatedEntity, String associatedEntityTable, String propertyName) {
		return tableName(ownerEntityTable + '_') + propertyToColumnName(propertyName);
	}

	/**
	 * Return the argument
	 */
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		return columnName(joinedColumn);
	}

	/**
	 * Return the property name or propertyTableName
	 */
	public String foreignKeyColumnName(String propertyName, String propertyEntityName,
			String propertyTableName, String referencedColumnName) {
		String header = null == propertyName ? propertyTableName : unqualify(propertyName);
		if (header == null) {
			throw new AssertionFailure("NamingStrategy not properly filled");
		}
		return columnName(header) + "_" + referencedColumnName;
	}

	/**
	 * Return the column name or the unqualified property name
	 */
	public String logicalColumnName(String columnName, String propertyName) {
		return StringHelper.isNotEmpty(columnName) ? columnName
				: propertyToColumnName(propertyName);
	}

	/**
	 * Returns either the table name if explicit or if there is an associated
	 * table, the concatenation of owner entity table and associated table
	 * otherwise the concatenation of owner entity table and the unqualified
	 * property name
	 */
	public String logicalCollectionTableName(String tableName, String ownerEntityTable,
			String associatedEntityTable, String propertyName) {
		if (tableName == null) {
			// use of a stringbuilder to workaround a JDK bug
			return new StringBuilder(ownerEntityTable).append("_")
					.append(
							associatedEntityTable == null ? unqualify(propertyName)
									: associatedEntityTable).toString();
		} else {
			return tableName;
		}
	}

	/**
	 * Return the column name if explicit or the concatenation of the property
	 * name and the referenced column
	 */
	public String logicalCollectionColumnName(String columnName, String propertyName,
			String referencedColumn) {
		return StringHelper.isNotEmpty(columnName) ? columnName : unqualify(propertyName) + "_"
				+ referencedColumn;
	}

	public Pluralizer getPluralizer() {
		return pluralizer;
	}

	public void setPluralizer(Pluralizer pluralizer) {
		this.pluralizer = pluralizer;
	}

	public boolean isPluralizeTableName() {
		return pluralizeTableName;
	}

	public void setPluralizeTableName(boolean pluralizeTableName) {
		this.pluralizeTableName = pluralizeTableName;
	}

}