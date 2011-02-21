package org.beanfuse.db.dialect;

public class SequenceSupport {

	private String createSql;
	private String dropSql;
	private String nextValSql;
	private String selectNextValSql;
	private String querySequenceSql;

	public SequenceSupport() {
		super();
		createSql = "create sequence {}";
		dropSql = "drop sequence {}";
	}

	public String getCreateSql() {
		return createSql;
	}

	public void setCreateSql(String createSql) {
		this.createSql = createSql;
	}

	public String getDropSql() {
		return dropSql;
	}

	public void setDropSql(String dropSql) {
		this.dropSql = dropSql;
	}

	public String getNextValSql() {
		return nextValSql;
	}

	public void setNextValSql(String nextValSql) {
		this.nextValSql = nextValSql;
	}

	public String getSelectNextValSql() {
		return selectNextValSql;
	}

	public void setSelectNextValSql(String selectNextValSql) {
		this.selectNextValSql = selectNextValSql;
	}

	public String getQuerySequenceSql() {
		return querySequenceSql;
	}

	public void setQuerySequenceSql(String querySequenceSql) {
		this.querySequenceSql = querySequenceSql;
	}

}
