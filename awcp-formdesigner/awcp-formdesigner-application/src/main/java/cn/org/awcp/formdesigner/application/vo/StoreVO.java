package cn.org.awcp.formdesigner.application.vo;

import java.io.Serializable;
import java.util.Date;

public class StoreVO implements Serializable{
	
	private static final long serialVersionUID = -4627045472998143241L;
	
	private String id;			//ID
	private String code;		//store分类
	private String name;		//名称
	private String content;		//内容
	private String description;	//描述
	private Long dynamicPageId;		//页面ID
	private String dynamicPageName;	//页面名称
	private Integer buttonGroup;	//按钮组
	private Integer order;		//顺序
	private Long systemId;		//系统ID
	
	private Integer isCheckOut;	//是否迁出
	private String checkOutUser;//迁出用户
	private String createdUser;	//创建用户
	private String updatedUser;	//修改用户
	private Date created;		//创建时间
	private Date updated;		//更改时间
	
	private String scope;		//用于数据回显不存放数据库

	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public Long getSystemId() {
		return systemId;
	}
	
	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}
	
	public Integer getButtonGroup() {
		return buttonGroup;
	}
	
	public void setButtonGroup(Integer buttonGroup) {
		this.buttonGroup = buttonGroup;
	}
	
	public Integer getOrder() {
		return order;
	}
	
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public String getDynamicPageName() {
		return dynamicPageName;
	}
	
	public void setDynamicPageName(String dynamicPageName) {
		this.dynamicPageName = dynamicPageName;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getDynamicPageId() {
		return dynamicPageId;
	}
	
	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}
	
	public Integer getIsCheckOut() {
		return isCheckOut;
	}
	
	public void setIsCheckOut(Integer isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getCheckOutUser() {
		return checkOutUser;
	}
	
	public void setCheckOutUser(String checkOutUser) {
		this.checkOutUser = checkOutUser;
	}
	
	public String getCreatedUser() {
		return createdUser;
	}
	
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
	public String getUpdatedUser() {
		return updatedUser;
	}
	
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getUpdated() {
		return updated;
	}
	
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}

