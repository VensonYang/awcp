package cn.org.awcp.core.domain.internal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import cn.org.awcp.core.domain.QueryCriterion;
import cn.org.awcp.core.domain.QueryException;

public class BetweenCriterion implements QueryCriterion{
	private String propName;
	private Comparable<?> from;
	private Comparable<?> to;

	public BetweenCriterion(String propName, Comparable<?> from, Comparable<?> to){
		if (StringUtils.isEmpty(propName)) {
			throw new QueryException("Property name is null!");
		}
		if (from == null) {
			throw new QueryException("From value is null!");
		}
		if (to == null) {
			throw new QueryException("To value is null!");
		}
		this.propName = propName;
		this.from = from;
		this.to = to;
	}

	public String getPropName() {
		return this.propName;
	}

	public Comparable<?> getFrom() {
		return this.from;
	}

	public Comparable<?> getTo() {
		return this.to;
	}

	public boolean equals(Object other){
		if (this == other)
			return true;
		if (!(other instanceof BetweenCriterion))
			return false;
		BetweenCriterion castOther = (BetweenCriterion)other;
		return new EqualsBuilder().append(getPropName(), castOther.getPropName()).append(this.from, castOther.from).append(this.to, castOther.to).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName()).append(this.from).append(this.to).toHashCode();
	}

	public String toString(){
		return getPropName() + " between " + this.from + " and " + this.to;
	}
}
