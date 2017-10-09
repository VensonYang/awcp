package org.szcloud.framework.unit.vo;

public class PunGroupSysVO {
	private Long groupSysId;
	private Long groupId;
	private Long sysId;
	
	public Long getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	public Long getSysId() {
		return sysId;
	}
	
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	
	public Long getGroupSysId() {
		return groupSysId;
	}
	
	public void setGroupSysId(Long groupSysId) {
		this.groupSysId = groupSysId;
	}

	@Override
	public String toString() {
		return "PunGroupSysVO [groupSysId=" + groupSysId + ", groupId=" + groupId + ", sysId=" + sysId + "]";
	}
	
}
