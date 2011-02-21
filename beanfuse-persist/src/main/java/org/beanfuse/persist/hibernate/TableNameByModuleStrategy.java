package org.beanfuse.persist.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *根据报名动态设置schema,prefix名字
 * 
 * @author chaostone
 * 
 */
public class TableNameByModuleStrategy {

	private static final Logger logger = LoggerFactory.getLogger(TableNameByModuleStrategy.class);
	private final List patterns = new ArrayList();

	private final Map packagePatterns = new HashMap();

	static final TableNameByModuleStrategy instance = new TableNameByModuleStrategy();

	static {
		instance.loadProperties();
	}

	/**
	 * load tablename-default.properties then load tablename.properties
	 */
	public void loadProperties() {
		try {
			Enumeration em = TableNameByModuleStrategy.class.getClassLoader().getResources(
					"META-INF/table.properties");
			while (em.hasMoreElements()) {
				loadProperties((URL) em.nextElement());
			}
			em = TableNameByModuleStrategy.class.getClassLoader().getResources("table.properties");
			while (em.hasMoreElements()) {
				loadProperties((URL) em.nextElement());
			}
		} catch (IOException e) {
			logger.error("load properties error", e);
		}
		Collections.sort(patterns);
		for (Iterator iter = patterns.iterator(); iter.hasNext();) {
			logger.info("table name pattern {}", iter.next());
		}

	}

	private void loadProperties(URL url) {
		try {
			logger.info("loading {}", url);
			InputStream is = url.openStream();
			Properties props = new Properties();
			if (null != is) {
				props.load(is);
			}
			for (Iterator iter = props.keySet().iterator(); iter.hasNext();) {
				String packageName = (String) iter.next();
				String schemaPrefix = props.getProperty(packageName).trim();

				String schema = null;
				String prefix = null;
				int commaIndex = schemaPrefix.indexOf(',');
				if (commaIndex < 0 || (commaIndex + 1 == schemaPrefix.length())) {
					schema = schemaPrefix;
				} else if (commaIndex == 0) {
					prefix = schemaPrefix.substring(1);
				} else {
					schema = StringUtils.substringBefore(schemaPrefix, ",");
					prefix = StringUtils.substringAfter(schemaPrefix, ",");
				}
				TableNamePattern pattern = (TableNamePattern) packagePatterns.get(packageName);
				if (null == pattern) {
					pattern = new TableNamePattern(packageName, schema, prefix);
					packagePatterns.put(packageName, pattern);
					patterns.add(pattern);
				} else {
					pattern.setSchema(schema);
					pattern.setPrefix(prefix);
				}
			}
			is.close();
		} catch (IOException e) {
			logger.error("property load error", e);
		}
	}

	public static String getSchema(String packageName) {
		String schemaName = null;
		for (Iterator iter = instance.getPatterns().iterator(); iter.hasNext();) {
			TableNamePattern packageSchema = (TableNamePattern) iter.next();
			if (packageName.indexOf(packageSchema.getPackageName()) == 0) {
				schemaName = packageSchema.getSchema();
			}
		}
		return schemaName;
	}

	public static String getPrefix(String packageName) {
		String prefix = null;
		for (Iterator iter = instance.getPatterns().iterator(); iter.hasNext();) {
			TableNamePattern packageSchema = (TableNamePattern) iter.next();
			if (packageName.indexOf(packageSchema.getPackageName()) == 0) {
				prefix = packageSchema.getPrefix();
			}
		}
		return prefix;
	}

	public List getPatterns() {
		return patterns;
	}

}

/**
 * 表命名模式
 * 
 * @author chaostone
 * 
 */
class TableNamePattern implements Comparable {
	// 报名
	String packageName;
	// 模式名
	String schema;
	// 前缀名
	String prefix;

	public TableNamePattern(String packageName, String schemaName, String prefix) {
		this.packageName = packageName;
		this.schema = schemaName;
		this.prefix = prefix;
	}

	public int compareTo(Object arg0) {
		TableNamePattern other = (TableNamePattern) arg0;
		return this.packageName.compareTo(other.packageName);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schemaName) {
		this.schema = schemaName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[package:").append(packageName).append(", schema:").append(schema);
		sb.append(", prefix:").append(prefix).append(']');
		return sb.toString();
	}

}