package org.beanfuse.model.pojo;

import java.util.Date;

import org.beanfuse.model.LongIdTimeEntity;

public class LongIdTimeObject extends LongIdObject implements LongIdTimeEntity {
	/** 创建时间 */
	protected Date createdAt;

	/** 最后修改时间 */
	protected Date updatedAt;

	public LongIdTimeObject() {
		super();
	}

	public LongIdTimeObject(Long id) {
		super(id);
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
