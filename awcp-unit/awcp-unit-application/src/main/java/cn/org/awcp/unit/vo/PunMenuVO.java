package cn.org.awcp.unit.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PunMenuVO {
	private Long menuId;
	private Long parentMenuId;
	private String menuType;
	private String menuName;
	private String menuIcon;
	private String menuAddress;
	private Long dynamicPageId;
	private Integer menuSeq;
	private String pid;
	private Long operateType;
	private Long sysId;
	private String dynamicpageName;// 动态表单名
	private int menuFlag;
	private int type;

	public int getMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(int menuFlag) {
		this.menuFlag = menuFlag;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 是否被选中（具有权限）;
	 */
	private boolean checked;

	public Long getMenuId() {
		return menuId;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Long getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	public String getMenuAddress() {
		return menuAddress;
	}

	public void setMenuAddress(String menuAddress) {
		this.menuAddress = menuAddress;
	}

	public Integer getMenuSeq() {
		return menuSeq;
	}

	public void setMenuSeq(Integer menuSeq) {
		this.menuSeq = menuSeq;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Long getOperateType() {
		return operateType;
	}

	public void setOperateType(Long operateType) {
		this.operateType = operateType;
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

	public String getDynamicpageName() {
		return dynamicpageName;
	}

	public void setDynamicpageName(String dynamicpageName) {
		this.dynamicpageName = dynamicpageName;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getMenuId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof PunMenuVO == false)
			return false;
		if (this == obj)
			return true;
		PunMenuVO other = (PunMenuVO) obj;
		return new EqualsBuilder().append(getMenuId(), other.getMenuId()).isEquals();
	}

	@Override
	public String toString() {
		return "PunMenuVO [menuId=" + menuId + ", parentMenuId=" + parentMenuId + ", menuType=" + menuType
				+ ", menuName=" + menuName + ", menuIcon=" + menuIcon + ", menuAddress=" + menuAddress
				+ ", dynamicPageId=" + dynamicPageId + ", menuSeq=" + menuSeq + ", pid=" + pid + ", operateType="
				+ operateType + ", sysId=" + sysId + ", dynamicpageName=" + dynamicpageName + ", menuFlag=" + menuFlag
				+ ", type=" + type + ", checked=" + checked + "]";
	}

}
