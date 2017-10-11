package cn.org.awcp.core.domain;

import java.security.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class OrderSetting{
	private boolean ascending = true;
	private String propName;

	public boolean isAscending() {
		return this.ascending;
	}

	public String getPropName() {
		return this.propName;
	}

	private OrderSetting(boolean ascending, String propName) {
		if (StringUtils.isEmpty(propName)) {
			throw new InvalidParameterException("propName should not be empty!");
		}
		this.ascending = ascending;
		this.propName = propName;
	}

	public static OrderSetting asc(String propName) {
		return new OrderSetting(true, propName);
	}

	public static OrderSetting desc(String propName) {
		return new OrderSetting(false, propName);
	}

	public boolean equals(Object other){
		if (this == other)
			return true;
		if (!(other instanceof OrderSetting))
			return false;
		OrderSetting castOther = (OrderSetting)other;
		return new EqualsBuilder().append(this.ascending, castOther.ascending).append(this.propName, castOther.propName).isEquals();
	}

	public int hashCode(){
		return new HashCodeBuilder(17, 37).append(this.ascending).append(this.propName).toHashCode();
	}

	public String toString(){
		return new StringBuilder().append(this.propName).append(this.ascending ? " ascending" : "descending").toString();
	}
}
