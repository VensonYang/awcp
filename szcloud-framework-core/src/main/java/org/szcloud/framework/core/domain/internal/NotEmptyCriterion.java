package org.szcloud.framework.core.domain.internal;

import org.szcloud.framework.core.domain.QueryCriterion;
import org.szcloud.framework.core.domain.QueryException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class NotEmptyCriterion implements QueryCriterion{
	private String propName;

	public NotEmptyCriterion(String propName) {
		if (StringUtils.isEmpty(propName)) {
			throw new QueryException("Property name is null!");
		}
		this.propName = propName;
	}

	public String getPropName() {
		return this.propName;
	}

	public boolean equals(Object other){    
		if (this == other)
			return true;
		if (!(other instanceof NotEmptyCriterion))
			return false;
		NotEmptyCriterion castOther = (NotEmptyCriterion)other;
		return new EqualsBuilder().append(getPropName(), castOther.getPropName()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName()).toHashCode();
	}

	public String toString() {
		return getPropName() + " is not empty";
	}
}
