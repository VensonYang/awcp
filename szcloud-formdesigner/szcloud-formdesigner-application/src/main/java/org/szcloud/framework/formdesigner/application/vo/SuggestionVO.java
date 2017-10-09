package org.szcloud.framework.formdesigner.application.vo;

import java.io.Serializable;
import java.util.Date;

public class SuggestionVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String dept;
	private String deptName;
	private Date date;
	private String conment;
	private String businessid;
	private String person;
	private String personName;
	private int flag;
	private String link;
	private String linkName;
	private int order;
	private String type;
	private String status;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDept() {
		return dept;
	}
	
	public void setDept(String dept) {
		this.dept = dept;
	}
		
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getConment() {
		return conment;
	}
	
	public void setConment(String conment) {
		this.conment = conment;
	}
	
	public String getBusinessid() {
		return businessid;
	}
	
	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}
	
	public String getPerson() {
		return person;
	}
	
	public void setPerson(String person) {
		this.person = person;
	}
	
	public int getFlag() {
		return flag;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
		
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getLinkName() {
		return linkName;
	}
	
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getPersonName() {
		return personName;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
}
