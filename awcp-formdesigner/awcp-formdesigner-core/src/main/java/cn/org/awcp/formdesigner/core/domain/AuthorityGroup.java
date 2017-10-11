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

public class AuthorityGroup implements Serializable{
	
	private static final long serialVersionUID = -4341550141207809604L;
	/**
	 * uuid 唯一标识
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 所属页面ID
	 */
	private Long dynamicPageId;
	/**
	 * 所属系统Id
	 */
	private Long systemId;
	/**
	 * 创建者
	 */
	private Long creater;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后更新人
	 */
	private Long lastupdater;
	/**
	 * 最后更新时间
	 */
	private Date lastupdateTime;
	/**
	 * 排序字段
	 */
	private String order;
	/**
	 * 描述
	 */
	private String description;

	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getRepository() {
		if (sqlSessionFactory == null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return AuthorityGroup.sqlSessionFactory;
	}
	
	public String save() {
		SqlSession session = getRepository().openSession();
		try {
			if (StringUtils.isNotBlank(this.getId())) {
				session.update(AuthorityGroup.class.getName() + ".update", this);
			} else {
				this.setId(UUID.randomUUID().toString());
				session.insert(AuthorityGroup.class.getName() + ".insert", this);
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
			session.delete(AuthorityGroup.class.getName() + ".remove", this);
			session.commit();
		} finally {
			session.clearCache();
			session.close();
		}
	}
	
	public static List<AuthorityGroup> selectByExample(BaseExample example) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(AuthorityGroup.class.getName() + ".selectByExample", example);
		} finally {
			session.close();
		}
	}
	
	public static AuthorityGroup get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			AuthorityGroup t = session.selectOne(AuthorityGroup.class.getName() + ".get", id);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
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

	public Long getLastupdater() {
		return lastupdater;
	}

	public void setLastupdater(Long lastupdater) {
		this.lastupdater = lastupdater;
	}

	public Date getLastupdateTime() {
		return lastupdateTime;
	}

	public void setLastupdateTime(Date lastupdateTime) {
		this.lastupdateTime = lastupdateTime;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
