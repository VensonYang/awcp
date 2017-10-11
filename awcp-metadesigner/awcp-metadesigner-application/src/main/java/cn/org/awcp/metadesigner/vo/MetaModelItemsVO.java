package cn.org.awcp.metadesigner.vo;

public class MetaModelItemsVO {
	
	//主键
	private Long id;
	
	//模型编号
	private Long modelId;
	
	//属性名称
	private String itemName;
	
	//属性编码
	private String itemCode;
	
	//属性类型
	private String itemType;

	//属性编号
	private String itemLength;
	
	//用主键
	private Integer usePrimaryKey;
	
	//用索引
	private Integer useIndex;
	
	//是否为空
	private Integer useNull;
	
	//是否有效
	private Integer itemValid;
	
	//默认值
	private String defaultValue;
	
	//备注
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemLength() {
		return itemLength;
	}

	public void setItemLength(String itemLength) {
		this.itemLength = itemLength;
	}

	public Integer getUsePrimaryKey() {
		return usePrimaryKey;
	}

	public void setUsePrimaryKey(Integer usePrimaryKey) {
		this.usePrimaryKey = usePrimaryKey;
	}

	public Integer getUseIndex() {
		return useIndex;
	}

	public void setUseIndex(Integer useIndex) {
		this.useIndex = useIndex;
	}

	public Integer getUseNull() {
		return useNull;
	}

	public void setUseNull(Integer useNull) {
		this.useNull = useNull;
	}

	public Integer getItemValid() {
		return itemValid;
	}

	public void setItemValid(Integer itemValid) {
		this.itemValid = itemValid;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString() {
		return "MetaModelItemsVO [id=" + id + ", modelId=" + modelId + ", itemName=" + itemName + ", itemCode="
				+ itemCode + ", itemType=" + itemType + ", itemLength=" + itemLength + ", usePrimaryKey="
				+ usePrimaryKey + ", useIndex=" + useIndex + ", useNull=" + useNull + ", itemValid=" + itemValid
				+ ", defaultValue=" + defaultValue + ", remark=" + remark + "]";
	}
	
}
