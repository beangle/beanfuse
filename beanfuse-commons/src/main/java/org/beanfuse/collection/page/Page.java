/*
 *
 * Copyright c 2005-2009.
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.  
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2008-4-29            Created
 *  
 ********************************************************************************/
package org.beanfuse.collection.page;

import java.util.List;

/**
 * 分页对象
 * 
 * @author chaostone
 * 
 */
public interface Page extends List {

	public static final int DEFAULT_PAGE_NUM = 1;

	public static final int DEFAULT_PAGE_SIZE = 20;

	public static final Page EMPTY_PAGE = new EmptyPage();

	/**
	 * 第一页.
	 * 
	 * @return 1
	 */
	public int getFirstPageNo();

	/**
	 * 最大页码
	 * 
	 * @return
	 */
	public int getMaxPageNo();

	/**
	 * 下一页页码
	 * 
	 * @return
	 */
	public int getNextPageNo();

	/**
	 * 上一页页码
	 * 
	 * @return
	 */
	public int getPreviousPageNo();

	/**
	 * 当前页码
	 * 
	 * @return
	 */
	public int getPageNo();

	/**
	 * 每页大小
	 * 
	 * @return
	 */
	public int getPageSize();

	/**
	 * 数据总量
	 * 
	 * @return
	 */
	public int getTotal();

	/**
	 * 下一页
	 */
	public Page next();

	/**
	 * 是否还有下一页
	 * 
	 * @return
	 */
	public boolean hasNext();

	/**
	 * 上一页
	 */
	public Page previous();

	/**
	 * 是否还有上一页
	 * 
	 * @return
	 */
	public boolean hasPrevious();

	/**
	 * 调转到指定页
	 * 
	 * @param pageNo
	 * @return
	 */
	public Page moveTo(int pageNo);

	public List getItems();

}
