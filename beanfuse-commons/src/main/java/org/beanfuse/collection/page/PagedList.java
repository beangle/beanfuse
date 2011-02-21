//$Id: PageAdapter.java 2008-8-7 下午04:02:50 chaostone Exp $
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
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      2008-8-7  Created
 *  
 ********************************************************************************/
package org.beanfuse.collection.page;

import java.util.List;

public class PagedList extends PageWapper {

	private final List datas;

	private int pageNo = 0;

	private int maxPageNo;

	private int pageSize;

	public PagedList(List datas, int pageSize) {
		this(datas, new PageLimit(1, pageSize));
	}

	public PagedList(List datas, PageLimit limit) {
		super();
		this.datas = datas;
		this.pageSize = limit.getPageSize();
		this.pageNo = limit.getPageNo() - 1;
		if (datas.size() <= pageSize) {
			this.maxPageNo = 1;
		} else {
			final int remainder = datas.size() % pageSize;
			final int quotient = datas.size() / pageSize;
			this.maxPageNo = (0 == remainder) ? quotient : (quotient + 1);
		}
		this.next();
	}

	public int getFirstPageNo() {
		return 1;
	}

	public int getMaxPageNo() {
		return maxPageNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotal() {
		return datas.size();
	}

	public final int getNextPageNo() {
		return getPage().getNextPageNo();
	}

	public final int getPreviousPageNo() {
		return getPage().getPreviousPageNo();
	}

	public boolean hasNext() {
		return getPageNo() < getMaxPageNo();
	}

	public boolean hasPrevious() {
		return getPageNo() > 1;
	}

	public Page next() {
		return moveTo(pageNo + 1);
	}

	public Page previous() {
		return moveTo(pageNo - 1);
	}

	public Page moveTo(int pageNo) {
		if (pageNo < 1) {
			throw new RuntimeException("error pageNo:" + pageNo);
		}
		this.pageNo = pageNo;
		int toIndex = pageNo * pageSize;
		SinglePage newPage = new SinglePage(pageNo, pageSize, datas.size(),
				datas.subList((pageNo - 1) * pageSize,
						(toIndex < datas.size()) ? toIndex : datas.size()));
		setPage(newPage);
		return this;
	}

}
