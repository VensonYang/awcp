package org.szcloud.framework.unit.core.domain;

import org.szcloud.framework.core.domain.BaseEntity;

public class SysDataSource extends BaseEntity {

	private static final long serialVersionUID = 1636699772682203735L;
	/*
	 * 系统ID
	 */
	private Long systemId;
	/*
	 * 数据源ID
	 */
	private Long dataSourceId;
	/*
	 * 是否默认数据源
	 */
	private Boolean isDefault = false;

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

}
