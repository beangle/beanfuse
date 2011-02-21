package org.beanfuse.db.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;

public final class DataSourceUtil {

	public static DataSource getDataSource(String datasourceName) {
		final Properties props = new Properties();
		try {
			InputStream is = DataSourceUtil.class.getResourceAsStream("/database.properties");
			if (null == is) {
				throw new RuntimeException("cannot find database.properties");
			}
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException("cannot find database.properties");
		}

		BasicDataSource datasource = new BasicDataSource();
		Enumeration names = props.propertyNames();
		boolean find = false;
		while (names.hasMoreElements()) {
			String propertyName = (String) names.nextElement();
			if (propertyName.startsWith(datasourceName + ".")) {
				find = true;
				try {
					PropertyUtils.setProperty(datasource, StringUtils.substringAfter(propertyName,
							datasourceName + "."), props.getProperty(propertyName));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (find) {
			return datasource;
		} else {
			return null;
		}
	}

	public static List getDataSourceNames() throws Exception {
		Properties props = new Properties();
		InputStream is = DataSourceUtil.class.getResourceAsStream("/database.properties");
		if (null != is) {
			props.load(is);
		} else {
			throw new RuntimeException("cannot find database.properties");
		}
		Set dialects = new HashSet();
		Enumeration names = props.propertyNames();
		while (names.hasMoreElements()) {
			String propertyName = (String) names.nextElement();
			String dialect = StringUtils.substringBefore(propertyName, ".");
			if (!dialects.contains(dialect)) {
				dialects.add(dialect);
			}
		}
		return new ArrayList(dialects);
	}

}
