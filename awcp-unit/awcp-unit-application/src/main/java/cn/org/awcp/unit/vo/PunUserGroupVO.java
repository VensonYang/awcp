package cn.org.awcp.unit.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.awcp.core.domain.BaseEntity;

public class PunUserGroupVO extends BaseEntity{

	private static final long serialVersionUID = -159069381025032124L;
	private Long userGruopId;
	private Long userId;
	private Long groupId;
	private Long positionId;
	private Boolean isManager;

	public PunUserGroupVO(){
	}

	public PunUserGroupVO(Long userGruopId){
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
		if(obj instanceof PunUserGroupVO == false) return false;
		if(this == obj) return true;
		PunUserGroupVO other = (PunUserGroupVO)obj;
		return new EqualsBuilder()
			.append(getUserGruopId(),other.getUserGruopId())
			.isEquals();
	}
}

