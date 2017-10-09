package org.szcloud.framework.formdesigner.application.vo;

import java.util.ArrayList;
import java.util.List;

public class ModelVO {
	private String id;
	private String modelName;
	private String modelAlias;
	private String modelCode;
	private  List<ModelItemVO> modelItems = new ArrayList<ModelItemVO>();
	
	public String getModelName() {
		return modelName;
	}
	
	public List<ModelItemVO> getModelItems() {
		return modelItems;
	}
	
	public void setModelItems(List<ModelItemVO> modelItems) {
		this.modelItems = modelItems;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getModelAlias() {
		return modelAlias;
	}
	
	public void setModelAlias(String modelAlias) {
		this.modelAlias = modelAlias;
	}
	
	public String getModelCode() {
		return modelCode;
	}
	
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	
}
