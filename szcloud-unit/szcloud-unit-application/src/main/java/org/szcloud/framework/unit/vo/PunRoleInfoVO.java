package org.szcloud.framework.unit.vo;

import org.szcloud.framework.unit.core.domain.PunSystem;

public class PunRoleInfoVO {
	private Long roleId;
	private Long sysId;
	private String roleName;
	private String dictRemark;
	private PunSystem punSystem;
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Long getSysId() {
		return sysId;
	}
	
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDictRemark() {
		return dictRemark;
	}
	
	public void setDictRemark(String dictRemark) {
		this.dictRemark = dictRemark;
	}
	
	public PunSystem getPunSystem() {
		return punSystem;
	}
	
	public void setPunSystem(PunSystem punSystem) {
		this.punSystem = punSystem;
	}

	@Override
	public String toString() {
		return "PunRoleInfoVO [roleId=" + roleId + ", sysId=" + sysId + ", roleName=" + roleName + ", dictRemark="
				+ dictRemark + ", punSystem=" + punSystem + "]";
	}
	
}
