package cn.org.awcp.formdesigner.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.utils.Springfactory;

public class AuthorityGroupWorkFlowNode implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String dynamicPageId;
	private String flowNode;
	private String authorityGroup;
	private String bakInfo;
	
	private Long creater;
	private Date createTime;
	private Date lastUpdateTime;
	private Long lastUpdater;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFlowNode() {
		return flowNode;
	}
	
	public void setFlowNode(String flowNode) {
		this.flowNode = flowNode;
	}
	
	public String getAuthorityGroup() {
		return authorityGroup;
	}
	
	public void setAuthorityGroup(String authorityGroup) {
		this.authorityGroup = authorityGroup;
	}
	
	public String getBakInfo() {
		return bakInfo;
	}
	
	public void setBakInfo(String bakInfo) {
		this.bakInfo = bakInfo;
	}

	public Long getCreater() {
		return creater;
	}
	
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public Long getLastUpdater() {
		return lastUpdater;
	}
	
	public void setLastUpdater(Long lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public String getDynamicPageId() {
		return dynamicPageId;
	}
	
	public void setDynamicPageId(String dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getRepository() {
		if (sqlSessionFactory == null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return AuthorityGroupWorkFlowNode.sqlSessionFactory;
	}
		
	public String save() {
		SqlSession session = getRepository().openSession();
		try {
			if (StringUtils.isNotBlank(this.getId())) {
				session.update(AuthorityGroupWorkFlowNode.class.getName() + ".update", this);
			} else {
				this.setId(UUID.randomUUID().toString());
				session.insert(AuthorityGroupWorkFlowNode.class.getName() + ".insert", this);
			}
			session.commit();
		} finally {
			session.close();
		}
		return this.id;
	}
	
	public void remove() {
		SqlSession session = getRepository().openSession();
		try {
			session.delete(AuthorityGroupWorkFlowNode.class.getName() + ".remove", this);
			session.commit();
		} finally {
			session.clearCache();
			session.close();
		}
	}
	
	public static List<AuthorityGroupWorkFlowNode> selectByExample(BaseExample example) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(AuthorityGroupWorkFlowNode.class.getName() + ".selectByExample", example);
		} finally {
			session.close();
		}
	}
	
	public static AuthorityGroupWorkFlowNode get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			AuthorityGroupWorkFlowNode t = session.selectOne(AuthorityGroupWorkFlowNode.class.getName() + ".get", id);
			return t;
		} finally {
			session.close();
		}
	}

}
