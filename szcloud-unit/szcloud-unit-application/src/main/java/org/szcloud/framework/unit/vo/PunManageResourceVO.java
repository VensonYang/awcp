package org.szcloud.framework.unit.vo;

public class PunManageResourceVO{
	/**
	 * 
	 */
	private Long resourceId;
	private Long sysId;
	private String resouType;
	private String relateResoId;
	private String whichEnd;

	public PunManageResourceVO(){
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getResouType() {
		return resouType;
	}

	public void setResouType(String resouType) {
		this.resouType = resouType;
	}

	public String getRelateResoId() {
		return relateResoId;
	}

	public void setRelateResoId(String relateResoId) {
		this.relateResoId = relateResoId;
	}

	public String getWhichEnd() {
		return whichEnd;
	}

	public void setWhichEnd(String whichEnd) {
		this.whichEnd = whichEnd;
	}

	@Override
	public String toString() {
		return "PunManageResourceVO [resourceId=" + resourceId + ", sysId=" + sysId + ", resouType=" + resouType
				+ ", relateResoId=" + relateResoId + ", whichEnd=" + whichEnd + "]";
	}
	
}

