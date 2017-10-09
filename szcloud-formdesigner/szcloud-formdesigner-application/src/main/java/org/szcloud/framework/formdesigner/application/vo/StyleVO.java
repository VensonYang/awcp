package org.szcloud.framework.formdesigner.application.vo;

/**
 * 存储在表p_fm_store
 * @author Administrator
 *
 */
public class StyleVO {
	private String pageId;		//p_fm_store表ID
	private String name;		//名称
	private String code;		//store类型
	private String description;	//描述
	private String script;		//样式脚本
	private Long systemId;		//系统ID
	
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
	
	public String getScript() {
		return script;
	}
	
	public void setScript(String script) {
		this.script = script;
	}
	
}
