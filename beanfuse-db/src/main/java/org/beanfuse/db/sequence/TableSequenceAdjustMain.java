package org.beanfuse.db.sequence;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.beanfuse.db.sequence.impl.DefaultSequenceNamePattern;
import org.beanfuse.db.sequence.impl.OracleTableSequenceDao;
import org.beanfuse.db.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableSequenceAdjustMain {

	public static void main(String args[]) throws Exception {
		Logger logger = LoggerFactory.getLogger(TableSequenceAdjustMain.class);
		boolean update = false;
		boolean remove = false;
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		options.addOption("update", false, "update sequence according to table ");
		options.addOption("drop", false, "remove sequence");
		CommandLine cmd = parser.parse(options, args);
		update = cmd.hasOption("update");
		remove = cmd.hasOption("drop");

		List datasourceNames = DataSourceUtil.getDataSourceNames();
		String dialect = null;
		PrintStream ps = System.out;
		if (datasourceNames.size() < 1) {
			logger.info("without any database config");
			return;
		} else if (datasourceNames.size() > 1) {
			ps.println("select db from " + datasourceNames + ":");
			while (null == dialect || !datasourceNames.contains(dialect)) {
				if (null != dialect) {
					ps.println("incorrect! select db from " + datasourceNames + ":");
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				dialect = br.readLine();
			}
		} else {
			dialect = (String) datasourceNames.iterator().next();
		}

		DataSource datasource = DataSourceUtil.getDataSource(dialect);
		OracleTableSequenceDao tableSequenceDao = new OracleTableSequenceDao();
		tableSequenceDao.setDataSource(datasource);
		tableSequenceDao.setRelation(new DefaultSequenceNamePattern());
		List sequences = tableSequenceDao.getInconsistent();
		info(sequences);
		if (update) {
			adjust(tableSequenceDao, sequences);
		}
		if (remove) {
			drop(tableSequenceDao, sequences);
		}
	}

	public static void drop(TableSequenceDao tableSequenceDao, List sequences) {
		PrintStream ps = System.out;
		if (!sequences.isEmpty()) {
			ps.println("start drop ...");
		}
		for (Iterator iter = sequences.iterator(); iter.hasNext();) {
			TableSequence seq = (TableSequence) iter.next();
			if (null == seq.getTableName()) {
				tableSequenceDao.drop(seq.getSeqName());
				ps.println("drop sequence " + seq.getSeqName());
			}
		}
	}

	public static void adjust(TableSequenceDao tableSequenceDao, List sequences) {
		PrintStream ps = System.out;
		if (!sequences.isEmpty()) {
			ps.println("start adjust ...");
		}
		for (Iterator iter = sequences.iterator(); iter.hasNext();) {
			TableSequence seq = (TableSequence) iter.next();
			if (null != seq.getTableName()) {
				ps.println("adjust sequence " + seq.getSeqName() + " with lastnumber "
						+ tableSequenceDao.adjust(seq));
			}
		}
		ps.println("finish adjust");
	}

	public static void info(List sequences) {
		PrintStream ps = System.out;
		if (sequences.isEmpty()) {
			ps.println("without any inconsistent  sequence");
		} else {
			ps.println("find inconsistent  sequence " + sequences.size());
			ps.println("sequence_name(lastnumber) table_name(max id)");
		}

		for (Iterator iter = sequences.iterator(); iter.hasNext();) {
			TableSequence seq = (TableSequence) iter.next();
			ps.println(seq);
		}
	}
}
