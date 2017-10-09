package org.szcloud.framework.unit.core.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.szcloud.framework.core.domain.BaseEntity;

/**
 * 用户组实体类
 * @author Administrator
 *
 */
public class PunUserGroup extends BaseEntity{
	private static final long serialVersionUID = 7188014221776696057L;
	private Long userGruopId;
	private Long userId;
	private Long groupId;
	private Long positionId;
	private Boolean isManager;

	public PunUserGroup(){
		
	}

	public PunUserGroup(Long userGruopId){
		this.userGruopId = userGruopId;
	}

	public void setUserGruopId(Long value) {
		this.userGruopId = value;
	}
	
	public Long getUserGruopId() {
		return this.userGruopId;
	}
	
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	
	public void setPositionId(Long value) {
		this.positionId = value;
	}
	
	public Long getPositionId() {
		return this.positionId;
	}
	
	public void setIsManager(Boolean value) {
		this.isManager = value;
	}
	
	public Boolean getIsManager() {
		return this.isManager;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("UserGruopId",getUserGruopId())
			.append("UserId",getUserId())
			.append("GroupId",getGroupId())
			.append("PositionId",getPositionId())
			.append("IsManager",getIsManager())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getUserGruopId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunUserGroup == false) return false;
		if(this == obj) return true;
		PunUserGroup other = (PunUserGroup)obj;
		return new EqualsBuilder()
			.append(getUserGruopId(),other.getUserGruopId())
			.isEquals();
	}
}

