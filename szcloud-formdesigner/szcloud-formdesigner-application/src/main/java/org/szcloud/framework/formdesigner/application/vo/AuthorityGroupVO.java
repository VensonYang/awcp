package org.szcloud.framework.formdesigner.application.vo;

import java.io.Serializable;
import java.util.Date;

public class AuthorityGroupVO implements Serializable{
	
	private static final long serialVersionUID = -4341550141207809604L;
	private String id;	//uuid 唯一标识
	private String name;//名称
	private Long dynamicPageId;//所属页面ID
	private Long systemId;	//所属系统Id
	private Long creater;	//创建者
	private Date createTime;//创建时间
	private Long lastupdater;	//最后更新人
	private Date lastupdateTime;	//最后更新时间
	private String order;	//排序字段
	private String description;	//描述
	//备用数据不存数据库用于数据库显示
	private String bakInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
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

	public Long getLastupdater() {
		return lastupdater;
	}

	public void setLastupdater(Long lastupdater) {
		this.lastupdater = lastupdater;
	}

	public Date getLastupdateTime() {
		return lastupdateTime;
	}

	public void setLastupdateTime(Date lastupdateTime) {
		this.lastupdateTime = lastupdateTime;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBakInfo() {
		return bakInfo;
	}

	public void setBakInfo(String bakInfo) {
		this.bakInfo = bakInfo;
	}
	
}
