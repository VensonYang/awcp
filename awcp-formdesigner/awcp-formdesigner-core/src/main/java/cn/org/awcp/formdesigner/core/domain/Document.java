package cn.org.awcp.formdesigner.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.utils.Springfactory;
import cn.org.awcp.formdesigner.core.domain.design.context.component.Component;
import cn.org.awcp.formdesigner.core.domain.design.context.data.DataDefine;

/**
 * 主要作为页面的数据载体，会关联页面和提交的数据
 * 模版+数据
 * 根据输出格式进行转化
 * @author lenovo
 *
 */
public class Document {
	private String id;
	private Date lastmodified;
	private String dynamicPageName;
	private String dynamicPageId;
	private Long authorId;
	private String author_group;
	private Date created = new Date(System.currentTimeMillis());
	private boolean isTmp;
	private String versions;
	private String parent;
	private String sortId;
	private String statelabel;
	private String initiator;
	private Date auditDate;
	private String auditUser;
	private String auditorNames;
	/**
	 * 记录状态  1：表示保存
	 * 		 0：表示提交
	 */
	private String state;
	private Integer stateint;
	private String lastmodifier;
	private String auditorList;
	private String recordId;
	private String instanceId;
	private String tableName;
	private String nodeId;
	private String workflowId;
	private String taskId;
	private String workItemId;
	private String flowTempleteId;
	private String entryId;
	/**
	 * 是新增or修改
	 */
	private boolean update = false;	
	/**
	 * 包装request传递过来的数据 
	 */
	private	Map<String, String> requestParams=new HashMap<String,String>();
	/**
	 * 根据requestParams和DynamicPage进行解析,key为数据定义的name
	 */
	private	Map<String, Map<String, String>> params=new HashMap<String, Map<String,String>>();
	/**
	 * 
	 */
	private Map<String, Component> components = new HashMap<String, Component>();
	/**
	 * 数据模型，key为数据模型名称
	 */
	private Map<String, DataDefine> datas = new HashMap<String, DataDefine>();

	private static SqlSessionFactory sqlSessionFactory;

	public  static SqlSessionFactory getRepository() {
		if(sqlSessionFactory==null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return Document.sqlSessionFactory;
	}

	public String getFlowTempleteId() {
		return flowTempleteId;
	}

	public void setFlowTempleteId(String flowTempleteId) {
		this.flowTempleteId = flowTempleteId;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String save() {
		SqlSession session = getRepository().openSession();
		try {
			if (StringUtils.isNotBlank(this.getId())) {
				session.update(Document.class.getName() + ".update", this);
			}else{
				this.setId(UUID.randomUUID().toString());
				session.insert(Document.class.getName() + ".insert", this);
			}
		} finally {
			session.close();
		}
		return this.id;
	}
	
	public void remove() {
		SqlSession session = getRepository().openSession();
		try {
			session.delete(Document.class.getName() + ".remove", this);
		} finally {
			session.close();
		}
	}

	public static List<Document> selectByExample(BaseExample example) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(Document.class.getName() + ".selectByExample", example);
		} finally {
			session.close();
		}
	}

	public static Document  findByWorkItemId(String flowTemplateId,String workItemId) {
		SqlSession session = getRepository().openSession();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("workItemId", workItemId);
			map.put("flowTemplateId", flowTemplateId);
			Document t = session.selectOne(Document.class.getName() + ".selectDocByWorkItemId", map);
			return t;
		} finally {
			session.close();
		}
	}

	public static Document get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			Document t = session.selectOne(Document.class.getName() + ".get", id);
			return t;
		} finally {
			session.close();
		}
	}
	
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
	
	public String getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public void setStateint(Integer stateint) {
		this.stateint = stateint;
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

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}

	public Map<String, Map<String, String>> getParams() {
		return params;
	}

	public void setParams(Map<String, Map<String, String>> params) {
		this.params = params;
	}

	public Map<String, Component> getComponents() {
		return components;
	}

	public void setComponents(Map<String, Component> components) {
		this.components = components;
	}

	public Map<String, DataDefine> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, DataDefine> datas) {
		this.datas = datas;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
