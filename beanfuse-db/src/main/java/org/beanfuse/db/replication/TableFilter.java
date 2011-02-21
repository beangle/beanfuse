package org.beanfuse.db.replication;

import java.util.Collection;

import org.apache.commons.collections.Predicate;

public interface TableFilter {

	public Collection filter(Collection<String> tables);

}
