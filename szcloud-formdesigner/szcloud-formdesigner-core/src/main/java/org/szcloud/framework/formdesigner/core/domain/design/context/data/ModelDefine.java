package org.szcloud.framework.formdesigner.core.domain.design.context.data;

import java.util.List;

import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;
import org.szcloud.framework.metadesigner.vo.MetaModelVO;


public class ModelDefine extends DataDefine {
	private MetaModelVO model;
	private List<MetaModelItemsVO> modelItems;
	private String modelCode;
	private String modelItemCodes;
	
	public MetaModelVO getModel() {
		return model;
	}
	public void setModel(MetaModelVO model) {
		this.model = model;
	}
	public List<MetaModelItemsVO> getModelItems() {
		return modelItems;
	}
	public void setModelItems(List<MetaModelItemsVO> modelItems) {
		this.modelItems = modelItems;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getModelItemCodes() {
		return modelItemCodes;
	}
	public void setModelItemCodes(String modelItemCodes) {
		this.modelItemCodes = modelItemCodes;
	}
}
