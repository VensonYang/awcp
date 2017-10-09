package org.szcloud.framework.unit.vo;

public class PunManageMenuVO{
	private Long menuId;
	private Long pumenuId;
	private Long parentMenuId;
	private String menuName;
	private String menuAddress;
	private Integer menuSeq;
	private String pid;
	private String menuType;
	private Long operateType;
	private String menuNote;
	private Long sysId;
	/**
	 * 是否被选中（具有权限）;
	 */
	private boolean checked;

	public PunManageMenuVO(){
	}

	public PunManageMenuVO(Long menuId){
		this.menuId = menuId;
	}

	public void setMenuId(Long value) {
		this.menuId = value;
	}
	
	public Long getMenuId() {
		return this.menuId;
	}
	
	public void setPumenuId(Long value) {
		this.pumenuId = value;
	}
	
	public Long getPumenuId() {
		return this.pumenuId;
	}
	
	public void setParentMenuId(Long value) {
		this.parentMenuId = value;
	}
	
	public Long getParentMenuId() {
		return this.parentMenuId;
	}
	
	public void setMenuName(String value) {
		this.menuName = value;
	}
	
	public String getMenuName() {
		return this.menuName;
	}
	
	public void setMenuAddress(String value) {
		this.menuAddress = value;
	}
	
	public String getMenuAddress() {
		return this.menuAddress;
	}
	
	public void setMenuSeq(Integer value) {
		this.menuSeq = value;
	}
	
	public Integer getMenuSeq() {
		return this.menuSeq;
	}
	
	public void setPid(String value) {
		this.pid = value;
	}
	
	public String getPid() {
		return this.pid;
	}
	
	public void setMenuType(String value) {
		this.menuType = value;
	}
	
	public String getMenuType() {
		return this.menuType;
	}
	
	public void setMenuNote(String value) {
		this.menuNote = value;
	}
	
	public String getMenuNote() {
		return this.menuNote;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getOperateType() {
		return operateType;
	}

	public void setOperateType(Long operateType) {
		this.operateType = operateType;
	}

	@Override
	public String toString() {
		return "PunManageMenuVO [menuId=" + menuId + ", pumenuId=" + pumenuId + ", parentMenuId=" + parentMenuId
				+ ", menuName=" + menuName + ", menuAddress=" + menuAddress + ", menuSeq=" + menuSeq + ", pid=" + pid
				+ ", menuType=" + menuType + ", operateType=" + operateType + ", menuNote=" + menuNote + ", sysId="
				+ sysId + ", checked=" + checked + "]";
	}
	
}

