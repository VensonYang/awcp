package cn.org.awcp.unit.core.domain;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseEntity;

/**
 * 菜单实体类
 * @author Administrator
 *
 */
public class PunMenu extends BaseEntity {
	private static final long serialVersionUID = 5676772407916526067L;
	private Long menuId;
	private Long parentMenuId;
	private String menuType;
	private String menuName;
	private String menuIcon;
	private Long dynamicPageId;
	private String menuAddress;
	private Integer menuSeq;
	private String pid;
	private Long sysId;
	private int menuFlag;
	private int type;
	private Long id;
	private PunMenu punMenu;

	public PunMenu() {
	}

	public PunMenu(Long menuId) {
		this.menuId = menuId;
	}
	
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

	public void setMenuId(Long value) {
		this.menuId = value;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setParentMenuId(Long value) {
		this.parentMenuId = value;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Long getParentMenuId() {
		return this.parentMenuId;
	}

	public void setMenuType(String value) {
		this.menuType = value;
	}

	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuName(String value) {
		this.menuName = value;
	}

	public Long getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
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

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public Integer getMenuSeq() {
		return this.menuSeq;
	}

	public void setPunMenu(PunMenu punMenu) {
		this.punMenu = punMenu;
	}

	public PunMenu getPunMenu() {
		return punMenu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("MenuId", getMenuId())
				.append("ParentMenuId", getMenuId()).append("MenuType", getMenuType()).append("MenuName", getMenuName())
				.append("MenuAddress", getMenuAddress()).append("MenuSeq", getMenuSeq()).append("Pid", getPid())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getMenuId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof PunMenu == false)
			return false;
		if (this == obj)
			return true;
		PunMenu other = (PunMenu) obj;
		return new EqualsBuilder().append(getMenuId(), other.getMenuId()).isEquals();
	}

	public void save() {
		PunMenu.getRepository().save(this);
	}

	public void remove() throws MRTException {
		PunMenu.getRepository().remove(this);
	}

	public static List<PunMenu> findAll() throws MRTException {
		return PunMenu.getRepository().findAll(PunMenu.class);
	}

}
