package org.szcloud.framework.core.domain.internal;

import org.szcloud.framework.core.domain.QueryCriterion;
import org.szcloud.framework.core.domain.QueryException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class StartsWithTextCriterion implements QueryCriterion {
	private String propName;
	private String value;

	public StartsWithTextCriterion(String propName, String value) {
		if (StringUtils.isEmpty(propName)) {
			throw new QueryException("Property name is null!");
		}
		if (StringUtils.isEmpty(value)) {
			throw new QueryException("Value is null!");
		}
		this.propName = propName;
		this.value = value;
	}

	public String getPropName() {
		return this.propName;
	}

	public String getValue() {
		return this.value;
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof StartsWithTextCriterion))
			return false;
		StartsWithTextCriterion castOther = (StartsWithTextCriterion)other;
		return new EqualsBuilder().append(getPropName(), castOther.getPropName()).append(this.value, castOther.value).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName()).append(this.value).toHashCode();
	}

	public String toString() {
		return getPropName() + " like '" + this.value + "*'";
	}
}

