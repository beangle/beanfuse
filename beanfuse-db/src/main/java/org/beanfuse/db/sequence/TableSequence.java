package org.beanfuse.db.sequence;


/**
 * @author cheneystar 2008-07-23
 */
public class TableSequence {

	/** sequence的名称 */
	private String seqName;

	/** 对应表名 */
	private String tableName;

	/** 对应列名 */
	private String idColumnName="id";

	/** sequence的最后一次执行的序列号 */
	private long lastNumber;

	/** 对应表中最大id */
	private long maxId;

	public long getLastNumber() {
		return lastNumber;
	}

	public void setLastNumber(long lastNumber) {
		this.lastNumber = lastNumber;
	}

	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	public long getMaxId() {
		return maxId;
	}

	public void setMaxId(long maxDataId) {
		this.maxId = maxDataId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdColumnName() {
		return idColumnName;
	}

	public void setIdColumnName(String columnName) {
		this.idColumnName = columnName;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(seqName).append('(').append(lastNumber).append(')');
		buffer.append("  ");
		if (null == tableName) {
			buffer.append("----");
		} else {
			buffer.append(tableName).append('(').append(maxId).append(')');
		}
		return buffer.toString();
	}

}
