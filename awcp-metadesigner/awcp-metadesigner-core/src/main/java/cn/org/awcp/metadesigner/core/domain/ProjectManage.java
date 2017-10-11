package cn.org.awcp.metadesigner.core.domain;

import cn.org.awcp.core.domain.BaseEntity;

public class ProjectManage extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String projectName;
	
	private String projectCode;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
