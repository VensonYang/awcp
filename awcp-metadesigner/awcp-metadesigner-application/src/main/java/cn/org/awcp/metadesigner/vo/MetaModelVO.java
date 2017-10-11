package cn.org.awcp.metadesigner.vo;

public class MetaModelVO {
	private Long id;
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
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setModelSynchronization(boolean modelSynchronization) {
		this.modelSynchronization = modelSynchronization;
	}
	
	public void setModelValid(boolean modelValid) {
		this.modelValid = modelValid;
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
	
	public Long getDataSourceId() {
		return dataSourceId;
	}
	
	public void setDataSourceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

}
