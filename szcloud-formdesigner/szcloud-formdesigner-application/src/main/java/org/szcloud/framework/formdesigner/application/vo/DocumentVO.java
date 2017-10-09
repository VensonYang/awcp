package org.szcloud.framework.formdesigner.application.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentVO {
	public static final String DOCUMENT_BODY = "html";
	public static final String DOCUMENT_SCRIPTS = "script";

	private String id;
	private Date lastmodified;
	private String dynamicPageName;
	private String dynamicPageId;
	private String workItemId;
	private String flowTempleteId;
	private String entryId;
	private Long authorId;
	private String author_group;
	private String selectId;

	private Date created = new Date();

	public String getFlowTempleteId() {
		return flowTempleteId;
	}

	public void setFlowTempleteId(String flowTempleteId) {
		this.flowTempleteId = flowTempleteId;
	}

	private boolean isTmp;

	private String versions;

	private String parent;
	private String sortId;
	private String statelabel;
	private String initiator;
	private Date auditDate;
	private String auditUser;
	private String auditorNames;
	private String state;
	private int stateint;
	private String lastmodifier;
	private String auditorList;
	private String recordId;
	private String instanceId;
	private String tableName;
	private String nodeId;
	private String taskId;
	private String orderBy;
	private String allowOrderBy;

	private String fid;

	private String appListPageId;

	public String getAppListPageId() {
		return appListPageId;
	}

	public void setAppListPageId(String appListPageId) {
		this.appListPageId = appListPageId;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * 静态网页的Id
	 */
	private String staticHtmlId;
	/**
	 * 是新增or修改
	 */
	private boolean update = false;
	/**
	 * 流程实例ID，流程ID在DynamicPage中指定
	 */
	private String workflowId;

	// 是否显示总页数，1是，0否
	private Integer showTotalCount;
	// 是否分页，1是，0否
	private Integer isLimitPage;
	// 每页显示数目
	private Long pageSize;
	// 是否显示序号列，1是，0否
	private Integer showReverseNum;
	// 序号显示的模式
	private String reverseNumMode;
	// 序号列排序方式，1降序，0升序
	private String reverseSortord;

	/**
	 * 包装request传递过来的数据
	 */
	private Map<String, String> requestParams = new HashMap<String, String>();
	/**
	 * 列表类型
	 */
	private Map<String, List<Map<String, String>>> listParams = new HashMap<String, List<Map<String, String>>>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDynamicPageName() {
		return dynamicPageName;
	}

	public void setDynamicPageName(String dynamicPageName) {
		this.dynamicPageName = dynamicPageName;
	}

	public String getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(String dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	public Long getAuthor() {
		return authorId;
	}

	public void setAuthor(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthor_group() {
		return author_group;
	}

	public void setAuthor_group(String author_group) {
		this.author_group = author_group;
	}

	public Date getCreated() {
		return created;
	}

	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public boolean isTmp() {
		return isTmp;
	}

	public void setTmp(boolean isTmp) {
		this.isTmp = isTmp;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getAuditorNames() {
		return auditorNames;
	}

	public void setAuditorNames(String auditorNames) {
		this.auditorNames = auditorNames;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAuditorList() {
		return auditorList;
	}

	public void setAuditorList(String auditorList) {
		this.auditorList = auditorList;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getStatelabel() {
		return statelabel;
	}

	public void setStatelabel(String statelabel) {
		this.statelabel = statelabel;
	}

	public int getStateint() {
		return stateint;
	}

	public void setStateint(int stateint) {
		this.stateint = stateint;
	}

	public String getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public String getLastmodifier() {
		return lastmodifier;
	}

	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}

	public Map<String, List<Map<String, String>>> getListParams() {
		return listParams;
	}

	public void setListParams(Map<String, List<Map<String, String>>> listParams) {
		this.listParams = listParams;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getAllowOrderBy() {
		return allowOrderBy;
	}

	public void setAllowOrderBy(String allowOrderBy) {
		this.allowOrderBy = allowOrderBy;
	}

	public String getStaticHtmlId() {
		return staticHtmlId;
	}

	public void setStaticHtmlId(String staticHtmlId) {
		this.staticHtmlId = staticHtmlId;
	}

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

	public String getReverseSortord() {
		return reverseSortord;
	}

	public void setReverseSortord(String reverseSortord) {
		this.reverseSortord = reverseSortord;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
}
