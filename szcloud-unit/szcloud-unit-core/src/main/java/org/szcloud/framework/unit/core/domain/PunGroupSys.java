package org.szcloud.framework.unit.core.domain;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseEntity;

public class PunGroupSys extends BaseEntity{
	
	private static final long serialVersionUID = -642108785424695932L;
	private Long groupSysId;
	private Long groupId;
	private Long sysId;
	private PunGroup punGroup;
	private PunSystem punSystem;
	
	public PunGroupSys(){
		
	}

	public PunGroupSys(Long groupSysId,Long groupId,Long sysId){
		this.groupSysId = groupSysId;
		this.groupId = groupId;
		this.sysId = sysId;
	}

	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	
	public void setSysId(Long value) {
		this.sysId = value;
	}
	
	public Long getSysId() {
		return this.sysId;
	}
	
	public Long getGroupSysId() {
		return groupSysId;
	}

	public void setGroupSysId(Long groupSysId) {
		this.groupSysId = groupSysId;
	}
	
	public void setPunGroup(PunGroup punGroup){
		this.punGroup = punGroup;
	}
	
	public PunGroup getPunGroup() {
		return punGroup;
	}
	
	public void setPunSystem(PunSystem punSystem){
		this.punSystem = punSystem;
	}
	
	public PunSystem getPunSystem() {
		return punSystem;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		    .append("GroupSysId", getGroupId())
			.append("GroupId",getGroupId())
			.append("SysId",getSysId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
		    .append(getGroupSysId())
			.append(getGroupId())
			.append(getSysId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PunGroupSys == false) return false;
		if(this == obj) return true;
		PunGroupSys other = (PunGroupSys)obj;
		return new EqualsBuilder()
			.append(getGroupId(),other.getGroupId())
			.append(getSysId(),other.getSysId())
			.append(getGroupSysId(), other.getGroupSysId())
			.isEquals();
	}
	
	public static List<PunGroupSys> findAll() throws MRTException{
		return PunGroupSys.getRepository().findAll(PunGroupSys.class);
	}
	
	public void save() throws MRTException{
		PunGroupSys.getRepository().save(this);
	}
	
	public void remove() throws MRTException{
		PunGroupSys.getRepository().remove(this);
	}
	
}

