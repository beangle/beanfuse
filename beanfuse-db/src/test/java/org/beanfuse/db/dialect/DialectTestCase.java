package org.beanfuse.db.dialect;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.beanfuse.db.meta.DatabaseMetadata;
import org.beanfuse.db.meta.SequenceMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DialectTestCase {
	protected Dialect dialect;
	protected DatabaseMetadata meta;

	protected static Logger log = LoggerFactory.getLogger(DialectTestCase.class.getName());

	protected void listMetadata() {
		Map tables = meta.getTables();
		for (Iterator iter = tables.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			log.info("table found {}", name);
		}

		Set seqs = meta.getSequences();
		for (Iterator iter = seqs.iterator(); iter.hasNext();) {
			SequenceMetadata obj = (SequenceMetadata) iter.next();
			log.info("sequence found {}", obj);
		}
	}
}
