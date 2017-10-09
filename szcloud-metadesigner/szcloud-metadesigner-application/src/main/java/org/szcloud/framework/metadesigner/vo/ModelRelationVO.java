package org.szcloud.framework.metadesigner.vo;

public class ModelRelationVO {
	
	private Long id;
	private String relationType;
	private String relationName;
	private long modelId;
	private long itemId;
	private String descript;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRelationType() {
		return relationType;
	}
	
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	public String getRelationName() {
		return relationName;
	}
	
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	
	public long getModelId() {
		return modelId;
	}
	
	public void setModelId(long modelId) {
		this.modelId = modelId;
	}
	
	public long getItemId() {
		return itemId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public String getDescript() {
		return descript;
	}
	
	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}
