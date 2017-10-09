package org.szcloud.framework.core.domain.internal;

import org.szcloud.framework.core.domain.QueryCriterion;
import org.szcloud.framework.core.domain.QueryException;

public class NotCriterion implements QueryCriterion{
	private QueryCriterion criterion;

	public NotCriterion(QueryCriterion criterion) {
		if (criterion == null) {
			throw new QueryException("Query criterion is null!");
		}
		this.criterion = criterion;
	}

	public QueryCriterion getCriteron() {
		return this.criterion;
	}
}
