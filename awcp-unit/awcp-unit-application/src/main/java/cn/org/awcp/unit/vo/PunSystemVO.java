package cn.org.awcp.unit.vo;

import java.io.Serializable;
import java.util.List;

public class PunSystemVO implements Serializable{
	
	private static final long serialVersionUID = -6743212079549798482L;
	private Long sysId;
	private String sysAddress;
	private String sysName;
	private String sysShortName;
	private String sysCreater;
	private java.util.Date sysCreateTime;
	private String sysCreateGroup;
	private String sysStatus;
	
	private Long groupId;
	
	private List<PunResourceVO> resources;
	
	public Long getSysId() {
		return sysId;
	}
	
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	
	public String getSysAddress() {
		return sysAddress;
	}
	
	public void setSysAddress(String sysAddress) {
		this.sysAddress = sysAddress;
	}
	
	public String getSysName() {
		return sysName;
	}
	
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	public String getSysShortName() {
		return sysShortName;
	}
	
	public void setSysShortName(String sysShortName) {
		this.sysShortName = sysShortName;
	}
	
	public String getSysCreater() {
		return sysCreater;
	}
	
	public void setSysCreater(String sysCreater) {
		this.sysCreater = sysCreater;
	}
	
	public java.util.Date getSysCreateTime() {
		return sysCreateTime;
	}
	
	public void setSysCreateTime(java.util.Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}
	
	public List<PunResourceVO> getResources() {
		return resources;
	}
	
	public void setResources(List<PunResourceVO> resources) {
		this.resources = resources;
	}
	
	public Long getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	public String getSysCreateGroup() {
		return sysCreateGroup;
	}
	
	public void setSysCreateGroup(String sysCreateGroup) {
		this.sysCreateGroup = sysCreateGroup;
	}
	
	public String getSysStatus() {
		return sysStatus;
	}
	
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}

	@Override
	public String toString() {
		return "PunSystemVO [sysId=" + sysId + ", sysAddress=" + sysAddress + ", sysName=" + sysName + ", sysShortName="
				+ sysShortName + ", sysCreater=" + sysCreater + ", sysCreateTime=" + sysCreateTime + ", sysCreateGroup="
				+ sysCreateGroup + ", sysStatus=" + sysStatus + ", groupId=" + groupId + ", resources=" + resources
				+ "]";
	}
	
}
