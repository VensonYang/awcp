package cn.org.awcp.unit.vo;

import java.util.Arrays;

public class PunRoleAccessVO{

	private Long roleAccId;
	private Long roleId;
	private Long resourceId;
	private Long operType;
	private Long[] resIDs;

	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
	
	public Long getResourceId() {
		return this.resourceId;
	}
	
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getRoleAccId() {
		return roleAccId;
	}

	public void setRoleAccId(Long roleAccId) {
		this.roleAccId = roleAccId;
	}

	public Long getOperType() {
		return operType;
	}

	public void setOperType(Long operType) {
		this.operType = operType;
	}

	public Long[] getResIDs() {
		return resIDs;
	}

	public void setResIDs(Long[] resIDs) {
		this.resIDs = resIDs;
	}

	@Override
	public String toString() {
		return "PunRoleAccessVO [roleAccId=" + roleAccId + ", roleId=" + roleId + ", resourceId=" + resourceId
				+ ", operType=" + operType + ", resIDs=" + Arrays.toString(resIDs) + "]";
	}
	
}

