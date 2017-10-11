package cn.org.awcp.core.domain.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import cn.org.awcp.core.domain.QueryCriterion;
import cn.org.awcp.core.domain.QueryException;

public class InCriterion implements QueryCriterion{
	private String propName;
	
	@SuppressWarnings("unchecked")
	private Collection<? extends Object> value = Collections.EMPTY_SET;

	public InCriterion(String propName, Collection<? extends Object> value){
		if (StringUtils.isEmpty(propName)) {
			throw new QueryException("Property name is null!");
		}
		this.propName = propName;
		if (value != null)
			this.value = value;
	}

	public InCriterion(String propName, Object[] value) {
		if (StringUtils.isEmpty(propName)) {
			throw new QueryException("Property name is null!");
		}
		this.propName = propName;
		if ((value != null) && (value.length > 0))
			this.value = Arrays.asList(value);
	}

	public String getPropName() {
		return this.propName;
	}

	public Collection<? extends Object> getValue() {
		return this.value;
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof InCriterion))
			return false;
		InCriterion castOther = (InCriterion)other;
		return new EqualsBuilder().append(getPropName(), castOther.getPropName()).append(this.value, castOther.value).isEquals();
	}

	public int hashCode(){
		return new HashCodeBuilder(17, 37).append(getPropName()).append(this.value).toHashCode();
	}

	public String toString(){
		return getPropName() + " in collection [" + collectionToString(this.value) + "]";
	}

	private String collectionToString(Collection<? extends Object> value) {
		return StringUtils.join(value, ",");
	}
}
