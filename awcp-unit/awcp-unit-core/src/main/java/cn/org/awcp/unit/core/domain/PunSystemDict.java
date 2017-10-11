package cn.org.awcp.unit.core.domain;

import cn.org.awcp.core.domain.BaseEntity;

/**
 * @author lenovo
 *
 */
public class PunSystemDict extends BaseEntity {
	private static final long serialVersionUID = -867508614491046926L;
	
	/*
	 * 
	 * 如：
	 * 	1.1 value1
	 * 	1.2 value2
	 * 	1.3 value3
	 * 	1.4 value4
	 * 
	 */
	private String code;
	/*
	 * 名称
	 */
	private String key;
	/*
	 * 对应值
	 */
	private String value;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
