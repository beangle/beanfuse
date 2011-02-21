package org.beanfuse.transfer.importer;

import java.util.Set;

import org.beanfuse.entity.Populator;

public interface EntityImporter extends Importer {

	public Set getForeignerKeys();

	public void addForeignedKeys(String foreignerKey);

	public void setPopulator(Populator populator);
}
