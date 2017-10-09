package org.szcloud.framework.unit.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.szcloud.framework.core.domain.BaseEntity;

public class PunUserRoleVO extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185542643133360L;
	private Long userRoleId;
	private Long userId;
	private Long roleId;

	public PunUserRoleVO(){
	}

	public PunUserRoleVO(Long userRoleId){
		this.userRoleId = userRoleId;
	}

	public void setUserRoleId(Long value) {
		this.userRoleId = value;
	}
	
	public Long getUserRoleId() {
		return this.userRoleId;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("UserRoleId",getUserRoleId())
			.append("UserId",getUserId())
			.append("RoleId",getRoleId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getUserRoleId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunUserRoleVO == false) return false;
		if(this == obj) return true;
		PunUserRoleVO other = (PunUserRoleVO)obj;
		return new EqualsBuilder()
			.append(getUserRoleId(),other.getUserRoleId())
			.isEquals();
	}
}

