package org.beanfuse.db.sequence;

import java.util.List;

/**
 * 
 * @author cheneystar 2008-07-23
 * 
 */
public interface TableSequenceDao {

	/** 得到所有用户的序列号* */
	public List getAll();

	/** 得到数据库中没有被指定的sequence* */
	public List getNoneReferenced();
	
	/**
	 * 找到所有错误的sequence
	 * 
	 * @return
	 */
	public List getInconsistent();
	
	/**
	 * 删除指定的sequence
	 * 
	 * @param sequence_name
	 * @return
	 */
	public boolean drop(String sequence_name);

	public void setRelation(SequenceNamePattern relation);

	public Long adjust(TableSequence tableSequence);
}
