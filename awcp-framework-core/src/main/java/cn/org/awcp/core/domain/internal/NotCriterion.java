package cn.org.awcp.core.domain.internal;

import cn.org.awcp.core.domain.QueryCriterion;
import cn.org.awcp.core.domain.QueryException;

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
