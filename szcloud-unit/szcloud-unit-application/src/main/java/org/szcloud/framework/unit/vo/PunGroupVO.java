package org.szcloud.framework.unit.vo;

import java.io.Serializable;

public class PunGroupVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7435988006814568339L;
	private Long groupId;
	private Long parentGroupId;
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
	private String number;
	
	public Long getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	public Long getParentGroupId() {
		return parentGroupId;
	}
	
	public void setParentGroupId(Long parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	
	public String getGroupType() {
		return groupType;
	}
	
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	public String getGroupChName() {
		return groupChName;
	}
	
	public void setGroupChName(String groupChName) {
		this.groupChName = groupChName;
	}
	
	public String getGroupShortName() {
		return groupShortName;
	}
	
	public void setGroupShortName(String groupShortName) {
		this.groupShortName = groupShortName;
	}
	
	public String getGroupEnName() {
		return groupEnName;
	}
	public void setGroupEnName(String groupEnName) {
		this.groupEnName = groupEnName;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getGroupAddress() {
		return groupAddress;
	}
	
	public void setGroupAddress(String groupAddress) {
		this.groupAddress = groupAddress;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getGroupBusinessSphere() {
		return groupBusinessSphere;
	}
	
	public void setGroupBusinessSphere(String groupBusinessSphere) {
		this.groupBusinessSphere = groupBusinessSphere;
	}
	
	public java.util.Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getNumber() {
		return number;
	}
	
	
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "PunGroupVO [groupId=" + groupId + ", parentGroupId=" + parentGroupId + ", groupType=" + groupType
				+ ", groupChName=" + groupChName + ", groupShortName=" + groupShortName + ", groupEnName=" + groupEnName
				+ ", orgCode=" + orgCode + ", groupAddress=" + groupAddress + ", zipCode=" + zipCode
				+ ", contactNumber=" + contactNumber + ", fax=" + fax + ", groupBusinessSphere=" + groupBusinessSphere
				+ ", createDate=" + createDate + ", pid=" + pid + ", number=" + number + "]";
	}
	
}
