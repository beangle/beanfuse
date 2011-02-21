package org.beanfuse.persist.query;

import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.SinglePage;
import org.beanfuse.persist.EntityService;
import org.beanfuse.query.AbstractQuery;
import org.beanfuse.query.limit.AbstractQueryPage;

public class QueryPage extends AbstractQueryPage {

	private EntityService entityService;

	public QueryPage(AbstractQuery query, EntityService entityService) {
		super(query);
		this.entityService = entityService;
		next();
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	public Page moveTo(int pageNo) {
		query.getLimit().setPageNo(pageNo);
		setPageData((SinglePage) entityService.search(query));
		return this;
	}
}
