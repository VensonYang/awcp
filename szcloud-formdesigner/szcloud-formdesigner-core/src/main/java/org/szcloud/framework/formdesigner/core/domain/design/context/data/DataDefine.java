package org.szcloud.framework.formdesigner.core.domain.design.context.data;

import java.util.List;

import org.szcloud.framework.formdesigner.convert.MutiStringConverter;
import org.szcloud.framework.formdesigner.core.domain.DynamicPage;
import org.szcloud.framework.metadesigner.vo.MetaModelItemsVO;
import org.szcloud.framework.metadesigner.vo.MetaModelVO;

import com.thoughtworks.xstream.annotations.XStreamConverter;


public class DataDefine  {
	
	private String id;
	private String name;
	private String type;
	private String description;
	private String dataJson;
	private DynamicPage holder;
	private MetaModelVO model;
	private List<MetaModelItemsVO> modelItems;
	private String modelCode;
	private String modelItemCodes;
	@XStreamConverter(value=MutiStringConverter.class)
	private String sqlScript;
	@XStreamConverter(value=MutiStringConverter.class)
	private String deleteSql;
	/**
	 * 是否单行记录
	 */
	private int isSingle;
	/**
	 * 如果是多行记录，是否分页
	 */
	private int isPage;
	/**
	 * 如果是多行记录，不分页，那么限定记录条数是多少
	 */
	private int limitCount;
	
	public String getDataJson() {
		return dataJson;
	}
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DynamicPage getHolder() {
		return holder;
	}
	public void setHolder(DynamicPage holder) {
		this.holder = holder;
	}
	
	public String getSqlScript() {
		return sqlScript;
	}
	public void setSqlScript(String sqlScript) {
		this.sqlScript = sqlScript;
	}
	public String getDeleteSql() {
		return deleteSql;
	}
	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}
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
	public int getIsPage() {
		return isPage;
	}
	public void setIsPage(int isPage) {
		this.isPage = isPage;
	}
	public int getIsSingle() {
		return isSingle;
	}
	public void setIsSingle(int isSingle) {
		this.isSingle = isSingle;
	}
	public int getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}
}
