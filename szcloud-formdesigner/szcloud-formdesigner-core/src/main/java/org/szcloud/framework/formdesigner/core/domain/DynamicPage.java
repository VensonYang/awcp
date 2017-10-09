package org.szcloud.framework.formdesigner.core.domain;
import java.util.Date;
import java.util.Map;

import org.szcloud.framework.formdesigner.core.domain.design.context.act.PageAct;
import org.szcloud.framework.formdesigner.core.domain.design.context.component.Component;
import org.szcloud.framework.formdesigner.core.domain.design.context.data.DataDefine;

/**
 * 包含模型、页面组件、动作
 * 	CRUD操作+预览操作
 */
public class DynamicPage extends PageObject {
	private static final long serialVersionUID = 4088550996251770416L;
	
	/**
	 * 模型通过配置生成template
	 */
	private String dataJson;
	/**
	 * 页面类型
	 * 		普通页面：无form标签包裹
	 * 		表单页面：有form标签包裹，有配置信息
	 * 		文档页面：有配置信息，有form标签包裹，有页面设计信息（页眉页脚页码，纸张大小等等），转换成pdf时，会根据配置等按模版转换成pdf
	 * 		复合页面：可能会有多个form标签，相互会有联系
	 * 	PageObjectType
	 */
	private int pageType;
	/**
	 * 是否记录操作历史
	 */
	private String isLog;
	/**
	 * 加载前脚本，后续限定可以做哪些事
	 */
	private String preLoadScript;
	/**
	 * 加载后脚本，后续限定可以做哪些事，貌似加载之后，只能调用js去动作？ 
	 */
	private String afterLoadScript;
	/**
	 * 创建时间
	 */
	private Date created;
	/**
	 * 更改时间
	 */
	private Date updated;
	
	/**
	 * 页面模版内容
	 */
	private String templateContext;
	
	/**
	 * 发布时存储包含的所有组件的ID
	 */
	private String stores;
	
	/**
	 * 流程信息
	 */
	private String workflowNodeInfo;
	
	/**
	 * 
	 * 关联模板Id
	 */
	private Long templateId;
	/**
	 * 系统Id
	 */
	private Long systemId;
	
	private String lineHeight;
	private String minLineCount;
	private String maxLineCount;
	private Long pdfTemplatePage;
	private String lineHeightType;
	private Integer isCheckOut;
	private String checkOutUser;
	private String createdUser;
	
	private String updatedUser;
	//是否显示总页数，1是，0否
	private Integer showTotalCount;
	//是否分页，1是，0否
	private Integer isLimitPage;
	//每页显示数目
	private Long pageSize;
	//是否显示序号列，1是，0否
	private Integer showReverseNum;
	//序号显示的模式
	private String reverseNumMode;
	//序号列排序方式，1降序，0升序
	private String reverseSortord;
	private Long modular;	//所属模块
	
	public Integer getShowTotalCount() {
		return showTotalCount;
	}
	
	public void setShowTotalCount(Integer showTotalCount) {
		this.showTotalCount = showTotalCount;
	}
	
	public Integer getIsLimitPage() {
		return isLimitPage;
	}
	
	public void setIsLimitPage(Integer isLimitPage) {
		this.isLimitPage = isLimitPage;
	}
	
	public Long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getShowReverseNum() {
		return showReverseNum;
	}
	
	public void setShowReverseNum(Integer showReverseNum) {
		this.showReverseNum = showReverseNum;
	}
	
	public String getReverseNumMode() {
		return reverseNumMode;
	}
	
	public void setReverseNumMode(String reverseNumMode) {
		this.reverseNumMode = reverseNumMode;
	}
	
	public Long getSystemId() {
		return systemId;
	}
	
	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}
	
	public int getPageType() {
		return pageType;
	}
	
	public void setPageType(int pageType) {
		this.pageType = pageType;
	}
	
	public String getIsLog() {
		return isLog;
	}
	
	public void setIsLog(String isLog) {
		this.isLog = isLog;
	}
	
	public String getDataJson() {
		return dataJson;
	}
	
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}
	
	public String getPreLoadScript() {
		return preLoadScript;
	}
	
	public void setPreLoadScript(String preLoadScript) {
		this.preLoadScript = preLoadScript;
	}
	
	public String getAfterLoadScript() {
		return afterLoadScript;
	}
	
	public void setAfterLoadScript(String afterLoadScript) {
		this.afterLoadScript = afterLoadScript;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getUpdated() {
		return updated;
	}
	
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	public String getWorkflowNodeInfo() {
		return workflowNodeInfo;
	}
	
	public void setWorkflowNodeInfo(String workflowNodeInfo) {
		this.workflowNodeInfo = workflowNodeInfo;
	}
	
	public String getTemplateContext() {
		return templateContext;
	}
	
	public void setTemplateContext(String templateContext) {
		this.templateContext = templateContext;
	}
	
	public String getStores() {
		return stores;
	}
	
	public void setStores(String stores) {
		this.stores = stores;
	}
	
	public Long getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	//自定义函数区	
	public Map<String, Component> getComponents(boolean refresh) {
		return null;
	}
	
	public Map<String, PageAct> getActs(boolean refresh) {
		return null;
	}
	
	public Map<String, DataDefine> getDatas(boolean refresh) {
		return null;
	}
	
	public String getLineHeight() {
		return lineHeight;
	}
	
	public void setLineHeight(String lineHeight) {
		this.lineHeight = lineHeight;
	}
	
	public String getMinLineCount() {
		return minLineCount;
	}
	
	public void setMinLineCount(String minLineCount) {
		this.minLineCount = minLineCount;
	}
	
	public String getMaxLineCount() {
		return maxLineCount;
	}
	
	public void setMaxLineCount(String maxLineCount) {
		this.maxLineCount = maxLineCount;
	}
	
	public Long getPdfTemplatePage() {
		return pdfTemplatePage;
	}
	
	public void setPdfTemplatePage(Long pdfTemplatePage) {
		this.pdfTemplatePage = pdfTemplatePage;
	}
	
	public String getLineHeightType() {
		return lineHeightType;
	}
	
	public void setLineHeightType(String lineHeightType) {
		this.lineHeightType = lineHeightType;
	}
	
	public Integer getIsCheckOut() {
		return isCheckOut;
	}
	
	public void setIsCheckOut(Integer isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getCheckOutUser() {
		return checkOutUser;
	}
	
	public void setCheckOutUser(String checkOutUser) {
		this.checkOutUser = checkOutUser;
	}
	
	public String getCreatedUser() {
		return createdUser;
	}
	
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
	public String getUpdatedUser() {
		return updatedUser;
	}
	
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	
	public String getReverseSortord() {
		return reverseSortord;
	}
	
	public void setReverseSortord(String reverseSortord) {
		this.reverseSortord = reverseSortord;
	}

	public Long getModular() {
		return modular;
	}

	public void setModular(Long modular) {
		this.modular = modular;
	}
		
}	
