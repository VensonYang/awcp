package org.szcloud.framework.unit.vo;

import org.szcloud.framework.core.domain.BaseEntity;

public class PunManageGroupVO extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9173976109337419944L;
	private Long groupId;
	private String groupType;
	private String groupChName;
	private String groupShortName;
	private String groupEnName;
	private String orgCode;
	private String groupAddress;
	private String zipCode;
	private String contactNumber;
	private String fax;
	private String groupBusinessSphere;
	private java.util.Date createDate;
	private String pid;

	public PunManageGroupVO(){
	}

	public PunManageGroupVO(Long groupId){
		this.groupId = groupId;
	}

	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	
	public void setGroupType(String value) {
		this.groupType = value;
	}
	
	public String getGroupType() {
		return this.groupType;
	}
	
	public void setGroupChName(String value) {
		this.groupChName = value;
	}
	
	public String getGroupChName() {
		return this.groupChName;
	}
	
	public void setGroupShortName(String value) {
		this.groupShortName = value;
	}
	
	public String getGroupShortName() {
		return this.groupShortName;
	}
	
	public void setGroupEnName(String value) {
		this.groupEnName = value;
	}
	
	public String getGroupEnName() {
		return this.groupEnName;
	}
	
	public void setOrgCode(String value) {
		this.orgCode = value;
	}
	
	public String getOrgCode() {
		return this.orgCode;
	}
	
	public void setGroupAddress(String value) {
		this.groupAddress = value;
	}
	
	public String getGroupAddress() {
		return this.groupAddress;
	}
	
	public void setZipCode(String value) {
		this.zipCode = value;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
	public void setContactNumber(String value) {
		this.contactNumber = value;
	}
	
	public String getContactNumber() {
		return this.contactNumber;
	}
	
	public void setFax(String value) {
		this.fax = value;
	}
	
	public String getFax() {
		return this.fax;
	}
	
	public void setGroupBusinessSphere(String value) {
		this.groupBusinessSphere = value;
	}
	
	public String getGroupBusinessSphere() {
		return this.groupBusinessSphere;
	}

	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public void setPid(String value) {
		this.pid = value;
	}
	
	public String getPid() {
		return this.pid;
	}

	@Override
	public String toString() {
		return "PunManageGroupVO [groupId=" + groupId + ", groupType=" + groupType + ", groupChName=" + groupChName
				+ ", groupShortName=" + groupShortName + ", groupEnName=" + groupEnName + ", orgCode=" + orgCode
				+ ", groupAddress=" + groupAddress + ", zipCode=" + zipCode + ", contactNumber=" + contactNumber
				+ ", fax=" + fax + ", groupBusinessSphere=" + groupBusinessSphere + ", createDate=" + createDate
				+ ", pid=" + pid + "]";
	}

}

