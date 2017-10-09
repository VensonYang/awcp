package org.szcloud.framework.unit.core.domain;

import org.szcloud.framework.core.domain.BaseEntity;

public class PunUserOrg extends BaseEntity{
	
	private static final long serialVersionUID = -2350252859044352077L;
	private Long userId;
	private Long orgId;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getOrgId() {
		return orgId;
	}
	
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}

