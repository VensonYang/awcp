package org.szcloud.framework.formdesigner.core.domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.utils.Springfactory;

public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;

	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getRepository() {
		if (sqlSessionFactory == null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return Attachment.sqlSessionFactory;
	}

	private String id;

	private String storageId;

	private String fileName;

	private Long userId;

	private String userName;

	private String contentType;

	private Long systemId;

	private Long size;

	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String save() {
		Date now = new Date();
		this.createTime = now;
		this.updateTime = now;
		SqlSession session = getRepository().openSession();
		try {
			// TODO by ayesd
			// this.setId(UUID.randomUUID().toString());

			session.insert(Attachment.class.getName() + ".insert", this);
			session.commit();
		} finally {
			session.close();
		}
		return this.id;
	}

	public String update() {
		this.updateTime = new Date();
		SqlSession session = getRepository().openSession();
		try {
			// TODO by ayesd
			// this.setId(UUID.randomUUID().toString());

			session.insert(Attachment.class.getName() + ".update", this);
			session.commit();
		} finally {
			session.close();
		}
		return this.id;
	}

	public void remove() {
		SqlSession session = getRepository().openSession();
		try {
			session.delete(Attachment.class.getName() + ".remove", this);
			session.commit();
		} finally {
			session.close();
		}
	}

	public static List<Store> selectByExample(BaseExample example) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(Attachment.class.getName() + ".selectByExample", example);
		} finally {
			session.close();
		}
	}

	public static Attachment get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			Attachment t = session.selectOne(Attachment.class.getName() + ".get", id);
			return t;
		} finally {
			session.close();
		}
	}

	public static Attachment getByStorage(String storage) {
		SqlSession session = getRepository().openSession();
		try {
			Attachment t = session.selectOne(Attachment.class.getName() + ".getByStorage", storage);
			return t;
		} finally {
			session.close();
		}
	}

	public InputStream getInputStreamByFolder() {
		FileInputStream is = null;
		if (this != null && StringUtils.isNotBlank(this.getStorageId())) {
			try {
				is = new FileInputStream(this.getStorageId());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return is;
	}

}
