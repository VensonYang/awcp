package cn.org.awcp.core.domain.internal;

import cn.org.awcp.core.domain.QueryCriterion;
import cn.org.awcp.core.domain.QueryException;

public class OrCriterion implements QueryCriterion {
	private QueryCriterion[] criterions;

	public OrCriterion(QueryCriterion[] criterions){
		if ((criterions == null) || (criterions.length < 2)) {
			throw new QueryException("At least two query criterions required!");
		}
		this.criterions = criterions;
	}

	public QueryCriterion[] getCriterons() {
		return this.criterions;
	}
}
