package cn.org.awcp.unit.vo;

import cn.org.awcp.core.domain.BaseEntity;

public class PunManageUserGroupVO extends BaseEntity{
	private static final long serialVersionUID = 5391551398184745505L;
	private Long userGruopId;
	private Long userId;
	private Long groupId;
	private Long positionId;
	private Boolean isManager;

	public PunManageUserGroupVO(){
	}

	public PunManageUserGroupVO(Long userGruopId){
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

	@Override
	public String toString() {
		return "PunManageUserGroupVO [userGruopId=" + userGruopId + ", userId=" + userId + ", groupId=" + groupId
				+ ", positionId=" + positionId + ", isManager=" + isManager + "]";
	}

}

