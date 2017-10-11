package cn.org.awcp.unit.core.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.awcp.core.domain.BaseEntity;

/**
 * 用户角色实体类
 * @author Administrator
 *
 */
public class PunUserRole extends BaseEntity{

	private static final long serialVersionUID = -4698751148111928244L;
	private Long userRoleId;
	private Long userId;
	private Long roleId;

	public PunUserRole(){
		
	}

	public PunUserRole(Long userRoleId){
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
		if(obj instanceof PunUserRole == false) return false;
		if(this == obj) return true;
		PunUserRole other = (PunUserRole)obj;
		return new EqualsBuilder()
			.append(getUserRoleId(),other.getUserRoleId())
			.isEquals();
	}
}

