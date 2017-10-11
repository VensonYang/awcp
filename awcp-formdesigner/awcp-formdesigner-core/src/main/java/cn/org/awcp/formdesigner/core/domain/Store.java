package cn.org.awcp.formdesigner.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.utils.Springfactory;

public class Store implements Serializable {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(Store.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 5669720151268872868L;
	/**
	 * 校验类型的code前缀
	 */
	public static final String VALIDATOR_CODE = "0.1.4.";
	/**
	 * 页面动作类型的code前缀
	 */
	public static final String PAGEACT_CODE = "0.1.5.";
	/**
	 * 样式类型的code前缀
	 */
	public static final String STYLE_CODE = "0.1.6.";
	/**
	 * 组件类型的code前缀
	 */
	public static final String COMPONENT_CODE = "0.1.7.";

	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getRepository() {
		if (sqlSessionFactory == null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return Store.sqlSessionFactory;
	}

	/**
	 * uuid 唯一标识
	 */
	private String id;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 逻辑存在状态 --是否被删除 1表示删除状态，0表示存储状态
	 */
	private String status;
	/**
	 * 所属页面ID
	 */
	private Long dynamicPageId;
	/**
	 * 所属按钮组
	 */
	private Integer buttonGroup;
	/**
	 * 排序字段
	 */
	private Integer order;
	/**
	 * 所属系统Id
	 */
	private Long systemId;

	private Integer isCheckOut;
	private String checkOutUser;
	private String createdUser;

	private String updatedUser;

	/**
	 * 创建时间
	 */
	private Date created;
	/**
	 * 更改时间
	 */
	private Date updated;

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public String getId() {
		return id;
	}

	public Long getDynamicPageId() {
		return dynamicPageId;
	}

	public void setDynamicPageId(Long dynamicPageId) {
		this.dynamicPageId = dynamicPageId;
	}

	public Integer getButtonGroup() {
		return buttonGroup;
	}

	public void setButtonGroup(Integer buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static void main(String[] args) {
		JSONObject o = JSONObject.parseObject(null);
		logger.debug(o + "");
	}

	public String save() {
		SqlSession session = getRepository().openSession();
		try {
			if (StringUtils.isNotBlank(this.getId())) {
				JSONObject o = JSONObject.parseObject(this.getContent());
				if (o.containsKey("pageId")) {
					o.put("pageId", this.getId());
				}
				this.setContent(o.toJSONString());
				session.update(Store.class.getName() + ".update", this);
			} else {
				this.setId(UUID.randomUUID().toString());
				JSONObject o = JSONObject.parseObject(this.getContent());
				if (o != null) {
					if (o.containsKey("pageId")) {
						o.put("pageId", this.getId());
					}
					this.setContent(o.toJSONString());
				}
				session.insert(Store.class.getName() + ".insert", this);
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
			session.delete(Store.class.getName() + ".remove", this);
			session.commit();
		} finally {
			session.clearCache();
			session.close();
		}
	}

	public static List<Store> selectByExample(BaseExample example) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(Store.class.getName() + ".selectByExample", example);
		} finally {
			session.close();
		}
	}

	public static Store get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			Store t = session.selectOne(Store.class.getName() + ".get", id);
			return t;
		} finally {
			session.close();
		}
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

}
