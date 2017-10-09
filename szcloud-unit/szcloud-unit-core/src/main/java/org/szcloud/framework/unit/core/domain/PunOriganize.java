package org.szcloud.framework.unit.core.domain;

import java.util.Date;

import org.szcloud.framework.core.domain.BaseEntity;

public class PunOriganize extends BaseEntity{
	
	private static final long serialVersionUID = 9200889246300170404L;
	private String name;
	private String described;
	private Date createDate;
	private Date updateDate;
	
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
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String toString() {
		return "PunOriganize [name=" + name + ", described=" + described + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((described == null) ? 0 : described.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PunOriganize other = (PunOriganize) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (described == null) {
			if (other.described != null)
				return false;
		} else if (!described.equals(other.described))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		return true;
	}
	
}

