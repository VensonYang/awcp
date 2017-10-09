package org.szcloud.framework.unit.vo;

import java.io.Serializable;

public class PdataDictionaryVO implements Serializable {
	
	private static final long serialVersionUID = -2549182792526202832L;
	private Long id;
	private String code;
	private String dataKey;
	private String dataValue;
	private Integer dataOrder;
	private String dictRemark;
	private Long level;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDataKey() {
		return dataKey;
	}
	
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
	
	public String getDataValue() {
		return dataValue;
	}
	
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	
	public Integer getDataOrder() {
		return dataOrder;
	}
	
	public void setDataOrder(Integer dataOrder) {
		this.dataOrder = dataOrder;
	}
	
	public String getDictRemark() {
		return dictRemark;
	}
	
	public void setDictRemark(String dictRemark) {
		this.dictRemark = dictRemark;
	}
	
	public Long getLevel() {
		return level;
	}
	
	public void setLevel(Long level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "PdataDictionaryVO [id=" + id + ", code=" + code + ", dataKey=" + dataKey + ", dataValue=" + dataValue
				+ ", dataOrder=" + dataOrder + ", dictRemark=" + dictRemark + ", level=" + level + "]";
	}

}
