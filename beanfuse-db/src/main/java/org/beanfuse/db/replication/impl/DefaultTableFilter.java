package org.beanfuse.db.replication.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.beanfuse.db.replication.TableFilter;

public class DefaultTableFilter implements TableFilter {

	List<String> excludes = new ArrayList();

	List<String> includes = new ArrayList();

	public List<String> getExcludes() {
		return excludes;
	}

	public Collection filter(Collection<String> tables) {
		List newTables = new ArrayList();
		for (String tableName : tables) {
			boolean passed = includes.isEmpty();
			for (String pattern : includes) {
				if (tableName.equalsIgnoreCase(pattern.toLowerCase())) {
					passed = true;
				}
			}
			if (passed) {
				for (String pattern : excludes) {
					if (tableName.toLowerCase().contains(pattern.toLowerCase())) {
						passed = false;
					}
				}
			}
			if (passed) {
				newTables.add(tableName);
			}
		}
		return newTables;
	}

	public void addExclude(String table) {
		excludes.add(table);
	}

	public List<String> getIncludes() {
		return includes;
	}

	public void addInclude(String table) {
		includes.add(table);
	}

}
