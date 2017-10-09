package org.szcloud.framework.formdesigner.application.vo;

import java.io.Serializable;
import java.util.Date;

public class AuthorityGroupWorkFlowNodeVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String dynamicPageId;
	private String flowNode;
	private String authorityGroup;
	private String bakInfo;
	private Long creater;
	private Date createTime;
	private Date lastUpdateTime;
	private Long lastUpdater;
	
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
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFlowNode() {
		return flowNode;
	}
	
	public void setFlowNode(String flowNode) {
		this.flowNode = flowNode;
	}
	
	public String getAuthorityGroup() {
		return authorityGroup;
	}
	
	public void setAuthorityGroup(String authorityGroup) {
		this.authorityGroup = authorityGroup;
	}
	
	public String getBakInfo() {
		return bakInfo;
	}
	
	public void setBakInfo(String bakInfo) {
		this.bakInfo = bakInfo;
	}
	
	public String getDynamicPageId() {
		return dynamicPageId;
	}
	
	public void setDynamicPageId(String dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}
	
}
