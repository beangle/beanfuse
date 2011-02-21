package org.beanfuse.db.sequence;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.beanfuse.db.sequence.impl.DefaultSequenceNamePattern;
import org.beanfuse.db.sequence.impl.OracleTableSequenceDao;
import org.beanfuse.db.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OracleTableSequenceDaoTest {
	Logger logger = LoggerFactory.getLogger(OracleTableSequenceDaoTest.class);

	OracleTableSequenceDao tableSequenceDao;

	protected String getDialect() {
		return "oracle";
	}

	@BeforeClass
	public void setUp() throws Exception {
		DataSource datasource = DataSourceUtil.getDataSource("oracle");
		if (null != datasource) {
			tableSequenceDao = new OracleTableSequenceDao();
			tableSequenceDao.setDataSource(datasource);
			tableSequenceDao.setRelation(new DefaultSequenceNamePattern());
		}
	}

	public void testGetSequences() {
		if (null != tableSequenceDao) {
			List sequences = tableSequenceDao.getAll();
			logger.info("find sequence {}", new Integer(sequences.size()));
		}
	}

	public void testGetNoneReferenced() {
		if (null != tableSequenceDao) {
			List sequences = tableSequenceDao.getNoneReferenced();
			logger.info("find none referenced sequence {}", new Integer(sequences.size()));
			logger.info("they are {}", sequences);
		}
	}

	public void testGetInconsistent() {
		if (null != tableSequenceDao) {
			List sequences = tableSequenceDao.getInconsistent();
			logger.info("find inconsistent  sequence {}", new Integer(sequences.size()));
			for (Iterator iter = sequences.iterator(); iter.hasNext();) {
				TableSequence seq = (TableSequence) iter.next();
				logger.info("{}", seq);
			}
		}
	}

	public void testAdjust() {
		if (null != tableSequenceDao) {
			List sequences = tableSequenceDao.getInconsistent();
			logger.info("addjust sequence {}", new Integer(sequences.size()));
			for (Iterator iter = sequences.iterator(); iter.hasNext();) {
				TableSequence seq = (TableSequence) iter.next();
				if (null != seq.getTableName()) {
					tableSequenceDao.adjust(seq);
				}
			}
		}
	}

	public void testDrop() {
		if (null != tableSequenceDao) {
			List sequences = tableSequenceDao.getNoneReferenced();
			logger.info("drop sequence {}", new Integer(sequences.size()));
			for (Iterator iter = sequences.iterator(); iter.hasNext();) {
				String seq = (String) iter.next();
				tableSequenceDao.drop(seq);
			}
		}
	}
}
