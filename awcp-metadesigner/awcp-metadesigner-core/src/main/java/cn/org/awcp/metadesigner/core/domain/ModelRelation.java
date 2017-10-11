package cn.org.awcp.metadesigner.core.domain;

import java.util.List;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseEntity;

public class ModelRelation extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String relationName;
	private String relationType;
	private Long modelId;
	private Long itemId;
	private String descript;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRelationName() {
		return relationName;
	}
	
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	
	public String getRelationType() {
		return relationType;
	}
	
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	public Long getModelId() {
		return modelId;
	}
	
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public String getDescript() {
		return descript;
	}
	
	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	public static List<ModelRelation>   findAll() throws MRTException{
		List<ModelRelation> treeMenuList = null;
		try {
			treeMenuList = findAll(ModelRelation.class);
		} catch (Exception e) {
			throw new  MRTException(e.getMessage(),e);
		}
		return treeMenuList;
	}
	
}
