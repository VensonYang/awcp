package cn.org.awcp.metadesigner.core.domain;

import cn.org.awcp.core.domain.BaseEntity;

public class MetaModelType extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String typeName;
	
	private String typeCode;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
}
