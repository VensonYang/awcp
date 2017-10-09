package org.szcloud.framework.unit.vo;

public class SysDataSourceVO {
	/*
	 * ID
	 */
	private Long id;
	/*
	 * 系统ID
	 */
	private Long systemId;
	/*
	 * 数据源ID
	 */
	private Long dataSourceId;
	/*
	 * 是否默认 数据源
	 */
	private Boolean isDefault=false;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSystemId() {
		return systemId;
	}
	
	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}
	
	public Long getDataSourceId() {
		return dataSourceId;
	}
	
	public void setDataSourceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	
	public Boolean getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "SysDataSourceVO [id=" + id + ", systemId=" + systemId + ", dataSourceId=" + dataSourceId
				+ ", isDefault=" + isDefault + "]";
	}
	
}
