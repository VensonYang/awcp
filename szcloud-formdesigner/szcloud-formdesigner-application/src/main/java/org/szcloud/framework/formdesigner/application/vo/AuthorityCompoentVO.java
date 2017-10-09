package org.szcloud.framework.formdesigner.application.vo;

import java.io.Serializable;
import java.util.Date;

public class AuthorityCompoentVO implements Serializable{

	private static final long serialVersionUID = 1L;	
	private String id;
	private String authorityGroupId;
	private String componentId;
	private String authorityValue;
	private Long creater;
	private Date createTime;
	private Date lastUpdateTime;
	private Long lastUpdater;
	//区分包含组件
	private String includeComponent;
	
	public String getIncludeComponent() {
		return includeComponent;
	}
	
	public void setIncludeComponent(String includeComponent) {
		this.includeComponent = includeComponent;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAuthorityGroupId() {
		return authorityGroupId;
	}
	
	public void setAuthorityGroupId(String authorityGroupId) {
		this.authorityGroupId = authorityGroupId;
	}
	
	public String getComponentId() {
		return componentId;
	}
	
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	
	public String getAuthorityValue() {
		return authorityValue;
	}
	
	public void setAuthorityValue(String authorityValue) {
		this.authorityValue = authorityValue;
	}
	
	public Long getCreater() {
		return creater;
	}
	
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public Long getLastUpdater() {
		return lastUpdater;
	}
	
	public void setLastUpdater(Long lastUpdater) {
		this.lastUpdater = lastUpdater;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
