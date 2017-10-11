package cn.org.awcp.core.domain.internal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import cn.org.awcp.core.domain.QueryCriterion;
import cn.org.awcp.core.domain.QueryException;

public class ContainsElementCriterion implements QueryCriterion{
	private String propName;
	private Object value;

	public ContainsElementCriterion(String propName, Object value){
		if (StringUtils.isEmpty(propName)) {
			throw new QueryException("Property name is null!");
		}
		if (value == null) {
			throw new QueryException("Value is null!");
		}
		this.propName = propName;
		this.value = value;
	}

	public String getPropName() {
		return this.propName;
	}

	public Object getValue() {
		return this.value;
	}

	public boolean equals(Object other){
		if (this == other)
			return true;
		if (!(other instanceof ContainsElementCriterion))
			return false;
		ContainsElementCriterion castOther = (ContainsElementCriterion)other;
		return new EqualsBuilder().append(getPropName(), castOther.getPropName()).append(this.value, castOther.value).isEquals();
	}

	public int hashCode(){
		return new HashCodeBuilder(17, 37).append(getPropName()).append(this.value).toHashCode();
	}

	public String toString(){
		return getPropName() + " contains " + this.value;
	}
}
