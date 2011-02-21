package org.beanfuse.webapp.database.action;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.webapp.database.service.SqlService;
import org.beanfuse.webapp.security.action.SecurityAction;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 * 查询
 * 
 * @author chaostone
 * 
 */
public class SqlExecuteAction extends SecurityAction {

	private SqlService sqlService;

	public String index() {
		return forward();
	}

	public String executeSql() {
		String sql = get("sql");
		if (StringUtils.isEmpty(sql)) {
			forward("result");
		}
		sql = sql.trim();
		if (sql.startsWith("select")) {
			SqlRowSet rowSet = sqlService.queryForRowSet(sql);
			SqlRowSetMetaData meta = rowSet.getMetaData();
			put("rowSet", rowSet);
			put("meta", meta);
		}else{
			sqlService.execute(sql);
		}
		return forward("result");
	}

	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}

}
