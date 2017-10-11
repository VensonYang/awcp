package cn.org.awcp.metadesigner.core.domain;

import java.util.List;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseEntity;

public class MetaModel extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//所属分类
	private Long modelClassId;
	//模型名称
	private String modelName;
	//模型编号
	private String modelCode;
	//模型描述
	private String modelDesc;
	//数据表名
	private String tableName;
	//所属项目
	private String projectName;
	//模型类型
	private Integer modelType;
	//是否同步数据库
	private Boolean modelSynchronization;
	//是否有效
	private Boolean modelValid;
	private Long systemId;
	private Long dataSourceId;
	
	public Long getSystemId() {
		return systemId;
	}
	
	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}
	
	public Long getModelClassId() {
		return modelClassId;
	}
	
	public void setModelClassId(Long modelClassId) {
		this.modelClassId = modelClassId;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getModelCode() {
		return modelCode;
	}
	
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	
	public String getModelDesc() {
		return modelDesc;
	}
	
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Integer getModelType() {
		return modelType;
	}
	
	public void setModelType(Integer modelType) {
		this.modelType = modelType;
	}
	
	public Boolean getModelSynchronization() {
		return modelSynchronization;
	}
	
	public void setModelSynchronization(Boolean modelSynchronization) {
		this.modelSynchronization = modelSynchronization;
	}
	
	public Boolean getModelValid() {
		return modelValid;
	}
	
	public void setModelValid(Boolean modelValid) {
		this.modelValid = modelValid;
	}
	
	public static List<MetaModel>  findAll() throws MRTException{
		List<MetaModel> treeMenuList = null;
		try {
			treeMenuList = findAll(MetaModel.class);
		} catch (Exception e) {
			throw new  MRTException(e.getMessage(),e);
		}
		return treeMenuList;
	}
	
	public Long getDataSourceId() {
		return dataSourceId;
	}
	
	public void setDataSourceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

}
