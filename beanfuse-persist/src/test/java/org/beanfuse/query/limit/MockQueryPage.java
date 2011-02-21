//$Id: TestQueryPage.java 2008-9-11 下午10:43:17 chaostone Exp $
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
 * chaostone      2008-9-11  Created
 *  
 ********************************************************************************/
package org.beanfuse.query.limit;

import java.util.ArrayList;
import java.util.List;

import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.SinglePage;
import org.beanfuse.query.AbstractQuery;

public class MockQueryPage extends AbstractQueryPage {

	public MockQueryPage() {

	}

	public MockQueryPage(AbstractQuery query) {
		super(query);
		next();
	}

	public Page moveTo(int pageNo) {
		SinglePage page = new SinglePage();
		page.setPageNo(pageNo);
		page.setPageSize(super.getPageSize());
		List datas = new ArrayList(getPageSize());
		for (int i = 0; i < getPageSize(); i++) {
			datas.add(String.valueOf(i) + " of " + pageNo);
		}
		page.setItems(datas);
		page.setTotal(100);
		setPageData(page);
		return this;
	}

}
