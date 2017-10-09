package org.szcloud.framework.formdesigner.application.vo;

import java.io.Serializable;
import java.util.Date;

public class WorkFlowTemplateVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String templateID;
	private String templateName;
	private String templateCode;
	private String typeId;
	private String typeName;
	private String templateIsValid;
	private String creator;
	private Date createDate;
	
	public String getTemplateID() {
		return templateID;
	}
	
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public String getTemplateCode() {
		return templateCode;
	}
	
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	
	public String getTypeId() {
		return typeId;
	}
	
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTemplateIsValid() {
		return templateIsValid;
	}
	
	public void setTemplateIsValid(String templateIsValid) {
		this.templateIsValid = templateIsValid;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
