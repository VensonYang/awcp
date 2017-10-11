package cn.org.awcp.core.domain.internal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import cn.org.awcp.core.domain.QueryCriterion;
import cn.org.awcp.core.domain.QueryException;

public class IsEmptyCriterion implements QueryCriterion {
	private String propName;

	public IsEmptyCriterion(String propName){
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
		if (!(other instanceof IsEmptyCriterion))
			return false;
		IsEmptyCriterion castOther = (IsEmptyCriterion)other;
		return new EqualsBuilder().append(getPropName(), castOther.getPropName()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName()).toHashCode();
	}

	public String toString(){
		return getPropName() + " is empty";
	}
}
