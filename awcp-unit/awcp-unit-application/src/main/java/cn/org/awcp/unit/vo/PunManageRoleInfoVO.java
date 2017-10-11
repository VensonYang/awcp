package cn.org.awcp.unit.vo;

public class PunManageRoleInfoVO{
	private Long roleId;
	private Long sysId;
	private String roleName;

	public PunManageRoleInfoVO(){
	}

	public PunManageRoleInfoVO(Long roleId){
		this.roleId = roleId;
	}

	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
	
	public void setSysId(Long value) {
		this.sysId = value;
	}
	
	public Long getSysId() {
		return this.sysId;
	}
	
	public void setRoleName(String value) {
		this.roleName = value;
	}
	
	public String getRoleName() {
		return this.roleName;
	}

	@Override
	public String toString() {
		return "PunManageRoleInfoVO [roleId=" + roleId + ", sysId=" + sysId + ", roleName=" + roleName + "]";
	}

}

