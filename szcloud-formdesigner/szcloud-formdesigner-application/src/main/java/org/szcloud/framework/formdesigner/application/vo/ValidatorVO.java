package org.szcloud.framework.formdesigner.application.vo;

public class ValidatorVO {
	protected String pageId;
	protected String code;
	protected String name;
	protected String description;
	protected Long systemId;
	protected String type;
	
	public Long getSystemId() {
		return systemId;
	}
	
	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}
	
	public String getPageId() {
		return pageId;
	}
	
	public void setPageId(String pageId) {
		this.pageId = pageId;
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
