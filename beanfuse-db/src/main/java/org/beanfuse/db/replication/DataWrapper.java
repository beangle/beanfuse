package org.beanfuse.db.replication;

import java.util.List;

import org.beanfuse.db.meta.TableMetadata;

public interface DataWrapper {
	/**
	 * 查询单独表的数据
	 * 
	 * @param tableName
	 * @return
	 */
	public List getData(String tableName);

	/**
	 * 查询单独表的数据
	 * 
	 * @param table
	 * @return
	 */
	public List getData(TableMetadata tableMetadata);

	/**
	 * 插入单独表的数据
	 * 
	 * @param table
	 * @param data
	 */
	public void pushData(TableMetadata tableMetadata, List data);
	
	/**
	 * 关闭数据链接
	 */
	public void close();

	/**
	 * 得到数据的数量
	 * @param table
	 * @return
	 */
	public int count(TableMetadata table);

}
