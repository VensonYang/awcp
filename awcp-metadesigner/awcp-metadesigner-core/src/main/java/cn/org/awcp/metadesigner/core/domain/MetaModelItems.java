package cn.org.awcp.metadesigner.core.domain;

import cn.org.awcp.core.domain.BaseEntity;

public class MetaModelItems extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private Long modelId;
	private String itemName;
	private String itemCode;
	private String itemType;
	private String itemLength;
	private Integer usePrimaryKey;
	private Integer useIndex;
	private Integer useNull;
	private Integer itemValid;
	private String defaultValue;
	private String remark;

	public MetaModelItems(){
	}

	public void setModelId(Long value) {
		this.modelId = value;
	}
	
	public Long getModelId() {
		return this.modelId;
	}
	
	public void setItemName(String value) {
		this.itemName = value;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	
	public void setItemCode(String value) {
		this.itemCode = value;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	
	public void setItemType(String value) {
		this.itemType = value;
	}
	
	public String getItemType() {
		return this.itemType;
	}
	
	public void setItemLength(String value) {
		this.itemLength = value;
	}
	
	public String getItemLength() {
		return this.itemLength;
	}
	
	public void setUsePrimaryKey(Integer value) {
		this.usePrimaryKey = value;
	}
	
	public Integer getUsePrimaryKey() {
		return this.usePrimaryKey;
	}
	
	public void setUseIndex(Integer value) {
		this.useIndex = value;
	}
	
	public Integer getUseIndex() {
		return this.useIndex;
	}
	
	public void setUseNull(Integer value) {
		this.useNull = value;
	}
	
	public Integer getUseNull() {
		return this.useNull;
	}
	
	public void setItemValid(Integer value) {
		this.itemValid = value;
	}
	
	public Integer getItemValid() {
		return this.itemValid;
	}
	
	public void setDefaultValue(String value) {
		this.defaultValue = value;
	}
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}

}

