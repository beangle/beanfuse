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

package org.beanfuse.query.limit;

import java.util.Iterator;

import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.collection.page.PageWapper;
import org.beanfuse.collection.page.SinglePage;
import org.beanfuse.query.AbstractQuery;

/**
 * 基于查询的分页。<br>
 * 当使用或导出大批量数据时，使用者仍以List的方式进行迭代。<br>
 * 该实现则是内部采用分页方式。
 * 
 * @author chaostone
 * 
 */
public abstract class AbstractQueryPage extends PageWapper {

	protected int pageNo = 0;

	protected int maxPageNo = 0;

	protected AbstractQuery query;

	abstract public Page moveTo(int pageNo);

	public AbstractQueryPage() {
		super();
	}

	public AbstractQueryPage(AbstractQuery query) {
		this.query = query;
		if (null != query) {
			if (null == query.getLimit()) {
				query.setLimit(new PageLimit(pageNo, Page.DEFAULT_PAGE_SIZE));
			} else {
				pageNo = query.getLimit().getPageNo() - 1;
			}
		}
	}

	/**
	 * 按照单个分页数据设置.
	 * 
	 * @param page
	 */
	protected void setPageData(SinglePage page) {
		setPage(page);
		this.pageNo = page.getPageNo();
		this.maxPageNo = page.getMaxPageNo();
	}

	public Page next() {
		return moveTo(pageNo + 1);
	}

	public Page previous() {
		return moveTo(pageNo - 1);
	}

	public boolean hasNext() {
		return maxPageNo > pageNo;
	}

	public boolean hasPrevious() {
		return pageNo > 1;
	}

	public int getFirstPageNo() {
		return 1;
	}

	public int getMaxPageNo() {
		return maxPageNo;
	}

	public int getNextPageNo() {
		return getPage().getNextPageNo();
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return query.getLimit().getPageSize();
	}

	public int getPreviousPageNo() {
		return getPage().getPreviousPageNo();
	}

	public int getTotal() {
		return getPage().getTotal();
	}

	public Iterator iterator() {
		return new PageIterator(this);
	}

}

class PageIterator implements Iterator {

	private AbstractQueryPage queryPage;

	private int dataIndex;

	public PageIterator(AbstractQueryPage queryPage) {
		this.queryPage = queryPage;
		this.dataIndex = 0;
	}

	public boolean hasNext() {
		return (dataIndex < queryPage.getPage().getItems().size()) || (queryPage.hasNext());
	}

	public Object next() {
		if (dataIndex < queryPage.getPage().size()) {
			return queryPage.getPage().getItems().get(dataIndex++);
		} else {
			queryPage.next();
			dataIndex = 0;
			return queryPage.getPage().getItems().get(dataIndex++);
		}
	}

	public void remove() {

	}

}
