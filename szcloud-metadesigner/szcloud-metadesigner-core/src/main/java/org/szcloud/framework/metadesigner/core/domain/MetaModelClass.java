package org.szcloud.framework.metadesigner.core.domain;

import java.util.List;

import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseEntity;

public class MetaModelClass extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String classCode;
	
	private Long sysId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public static List<MetaModelClass>   findAll() throws MRTException{
		List<MetaModelClass> treeMenuList = null;
		try {
			treeMenuList =  findAll(MetaModelClass.class);
		} catch (Exception e) {
			throw new  MRTException(e.getMessage(),e);
		}
		return treeMenuList;
	}
}
