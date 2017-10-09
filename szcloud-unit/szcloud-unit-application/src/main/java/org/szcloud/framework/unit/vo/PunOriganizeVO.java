package org.szcloud.framework.unit.vo;

import org.szcloud.framework.core.domain.BaseEntity;

public class PunOriganizeVO extends BaseEntity{

	private static final long serialVersionUID = 9200889246300170404L;
	private String name;
	private String described;
	private java.util.Date createDate;
	private java.util.Date updateDate;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescribed() {
		return described;
	}
	
	public void setDescribed(String described) {
		this.described = described;
	}
	
	public java.util.Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "PunOriganizeVO [name=" + name + ", described=" + described + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}
	
}

