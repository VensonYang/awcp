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

public class AuthorityCompoent implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String authorityGroupId;
	private String componentId;
	private String authorityValue;
	private Long creater;
	private Date createTime;
	private Date lastUpdateTime;
	private Long lastUpdater;
	//区分包含组件
	private String includeComponent;

	public String getIncludeComponent() {
		return includeComponent;
	}
	public void setIncludeComponent(String includeComponent) {
		this.includeComponent = includeComponent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAuthorityGroupId() {
		return authorityGroupId;
	}

	public void setAuthorityGroupId(String authorityGroupId) {
		this.authorityGroupId = authorityGroupId;
	}
	
	public String getComponentId() {
		return componentId;
	}
	
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	
	public String getAuthorityValue() {
		return authorityValue;
	}
	
	public void setAuthorityValue(String authorityValue) {
		this.authorityValue = authorityValue;
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

	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getRepository() {
		if (sqlSessionFactory == null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return AuthorityCompoent.sqlSessionFactory;
	}
	
	public String save() {
		SqlSession session = getRepository().openSession();
		try {
			if (StringUtils.isNotBlank(this.getId())) {
				session.update(AuthorityCompoent.class.getName() + ".update", this);
			} else {
				this.setId(UUID.randomUUID().toString());
				session.insert(AuthorityCompoent.class.getName() + ".insert", this);
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
			session.delete(AuthorityCompoent.class.getName() + ".remove", this);
			session.commit();
		} finally {
			session.clearCache();
			session.close();
		}
	}
	
	public static List<AuthorityCompoent> selectByExample(BaseExample example) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(AuthorityCompoent.class.getName() + ".selectByExample", example);
		} finally {
			session.close();
		}
	}
	
	public static AuthorityCompoent get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			AuthorityCompoent t = session.selectOne(AuthorityCompoent.class.getName() + ".get", id);
			return t;
		} finally {
			session.close();
		}
	}
	
	public static AuthorityCompoent getByComponent(Serializable componentId) {
		SqlSession session = getRepository().openSession();
		try {
			AuthorityCompoent t = session.selectOne(AuthorityCompoent.class.getName() + ".getByComponent",componentId);
			return t;
		} finally {
			session.close();
		}
	}
	
}
