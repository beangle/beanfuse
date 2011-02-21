package org.beanfuse.db.sequence.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.beanfuse.db.sequence.TableSequence;
import org.beanfuse.db.sequence.TableSequenceDao;
import org.beanfuse.db.sequence.SequenceNamePattern;
import org.springframework.jdbc.core.JdbcTemplate;

public class OracleTableSequenceDao extends JdbcTemplate implements TableSequenceDao {

	private SequenceNamePattern relation;

	public boolean drop(String sequence_name) {
		String sql = "drop sequence " + sequence_name;
		execute(sql);
		return true;
	}

	public List getInconsistent() {
		ArrayList err_seqs = new ArrayList();
		List list = getAll();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			String seqName = (String) iter.next();
			String tempSeqSql = "select last_number from user_sequences seqs where seqs.sequence_name='"
					+ seqName + "'";
			long seqLast_number = queryForLong(tempSeqSql);
			String tableName = relation.getTableName(seqName);
			boolean exists = queryForInt("select count(*) from user_tables tbl where tbl.table_name='"
					+ tableName + "'") > 0;
			if (exists) {
				long dataCount = queryForLong("select count(*) from " + tableName);
				if (dataCount > 0) {
					long tableLMaxId = queryForLong("select max(id) from  " + tableName);
					if (seqLast_number < tableLMaxId) {
						TableSequence seq = new TableSequence();
						seq.setSeqName(seqName);
						seq.setTableName(tableName);
						seq.setLastNumber(seqLast_number);
						seq.setMaxId(tableLMaxId);
						err_seqs.add(seq);
					}
				}
			} else {
				TableSequence seq = new TableSequence();
				seq.setSeqName(seqName);
				seq.setLastNumber(seqLast_number);
				err_seqs.add(seq);
			}
		}
		return err_seqs;
	}

	/**
	 * ÐÞ¸´seq
	 * 
	 * @param sequence
	 * @param table
	 * @param column
	 */
	public Long adjust(TableSequence tableSequence) {
		String sequence = tableSequence.getSeqName();
		String getSql = "select " + sequence + ".nextval from dual";
		long current = queryForLong(getSql);
		String countSql = "select max(" + tableSequence.getIdColumnName() + ") maxid from "
				+ tableSequence.getTableName();
		List rs = queryForList(countSql);
		long max = 0;
		if (!rs.isEmpty()) {
			Map maxNum = new CaseInsensitiveMap((Map) rs.get(0));
			max = ((Number) maxNum.get("maxid")).longValue();
		}
		long repaired = 0;
		String updateIncrease = null;
		if (max > current) {
			if (max - current > 1) {
				updateIncrease = "ALTER SEQUENCE " + sequence + " INCREMENT BY   "
						+ (max - current - 1);
				execute(updateIncrease);
				queryForLong(getSql);
				//
				updateIncrease = "ALTER SEQUENCE " + sequence + " INCREMENT BY  1";
				execute(updateIncrease);
			}
			repaired = queryForLong(getSql);
		} else {
			if (1 == current)
				return new Long(1);
			updateIncrease = "ALTER SEQUENCE " + sequence + " INCREMENT BY  -1";
			execute(updateIncrease);

			repaired = queryForLong(getSql);

			updateIncrease = "ALTER SEQUENCE " + sequence + " INCREMENT BY  1";
			execute(updateIncrease);
		}
		return new Long(repaired);
	}

	public List getAll() {
		String sql = "select sequence_name from user_sequences order by sequence_name";
		List seqs = queryForList(sql);
		List sequenceNames = new ArrayList(seqs.size());
		for (Iterator iter = seqs.iterator(); iter.hasNext();) {
			Map seqNameMap = new CaseInsensitiveMap((Map) iter.next());
			String name = (String) seqNameMap.get("sequence_name");
			if (null == name)
				continue;
			sequenceNames.add(name);
		}
		return sequenceNames;
	}

	public List getNoneReferenced() {
		ArrayList err_seqs = new ArrayList();
		List list = getAll();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			String seqName = (String) iter.next();
			String tableName = relation.getTableName(seqName);
			boolean exists = queryForInt("select count(*) from user_tables tbl where tbl.table_name='"
					+ tableName + "'") > 0;
			if (!exists) {
				err_seqs.add(seqName);
			}
		}
		return err_seqs;
	}

	public void setRelation(SequenceNamePattern relation) {
		this.relation = relation;
	}

}
