package cn.org.awcp.core.domain.internal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import cn.org.awcp.core.domain.QueryCriterion;
import cn.org.awcp.core.domain.QueryException;

public class ContainsTextCriterion implements QueryCriterion{
	private String propName;
	private String value;

	public ContainsTextCriterion(String propName, String value) {
		if (StringUtils.isEmpty(propName)) {
			throw new QueryException("Property name is null!");
		}
		if (StringUtils.isEmpty(value)) {
			throw new QueryException("Value is empty!");
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

	public boolean equals(Object other){
		if (this == other)
			return true;
		if (!(other instanceof ContainsTextCriterion))
			return false;
		ContainsTextCriterion castOther = (ContainsTextCriterion)other;
		return new EqualsBuilder().append(getPropName(), castOther.getPropName()).append(this.value, castOther.value).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName()).append(this.value).toHashCode();
	}

	public String toString(){
		return getPropName() + " like '*" + this.value + "*'";
	}
}
