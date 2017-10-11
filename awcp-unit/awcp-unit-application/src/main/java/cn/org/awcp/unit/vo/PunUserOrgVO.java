package cn.org.awcp.unit.vo;

import cn.org.awcp.core.domain.BaseEntity;

public class PunUserOrgVO extends BaseEntity{

	private static final long serialVersionUID = -9202283825117691027L;
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

