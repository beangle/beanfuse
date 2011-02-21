package org.beanfuse.model;

import java.util.Date;

public interface LongIdTimeEntity extends LongIdEntity {
	/**
	 * 读取创建时间.
	 */
	public Date getCreatedAt();

	/**
	 * 设置创建时间.
	 * 
	 * @param createdAt
	 */
	public void setCreatedAt(Date createdAt);

	/**
	 * 读取修改时间.
	 * 
	 * @return
	 */
	public Date getUpdatedAt();

	/**
	 * 设置修改时间.
	 * 
	 * @param updatedAt
	 */
	public void setUpdatedAt(Date updatedAt);

}
