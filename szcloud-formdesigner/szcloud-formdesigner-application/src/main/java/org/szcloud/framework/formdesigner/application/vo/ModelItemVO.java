package org.szcloud.framework.formdesigner.application.vo;

public class ModelItemVO {
	private String id;
	private String name;
	private String modelId;
	private String itemCode;
	private String itemName;
	private String defaultValue;
	private String itemType;
	private Integer useNull;
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	private Integer itemLength;
	
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public Integer getUseNull() {
		return useNull;
	}
	
	public void setUseNull(Integer useNull) {
		this.useNull = useNull;
	}
	
	public Integer getItemLength() {
		return itemLength;
	}
	
	public void setItemLength(Integer itemLength) {
		this.itemLength = itemLength;
	}
	
	public String getModelId() {
		return modelId;
	}
	
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
