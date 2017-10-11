package cn.org.awcp.formdesigner.core.domain;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.org.awcp.core.utils.Springfactory;

public class CommondWords implements Serializable {
	private static final long serialVersionUID = 1L;

	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getRepository() {
		if (sqlSessionFactory == null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return CommondWords.sqlSessionFactory;
	}

	private String id;

	private Long userId;

	private String wordContent;

	private String typeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWordContent() {
		return wordContent;
	}

	public void setWordContent(String wordContent) {
		this.wordContent = wordContent;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String save() {
		SqlSession session = getRepository().openSession();
		try {
			if (StringUtils.isNotBlank(this.getId())) {
				session.update(CommondWords.class.getName() + ".update", this);
			} else {
				this.setId(UUID.randomUUID().toString());
				session.insert(CommondWords.class.getName() + ".insert", this);
			}
			session.commit();
		} finally {
			session.close();
		}
		return this.id;
	}

	public void remove(String id,Long user_id) {
		SqlSession session = getRepository().openSession();
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("id",id);
		paramMap.put("userId",Long.toString(user_id));
		try {
			session.delete(CommondWords.class.getName() + ".remove", paramMap);
			session.commit();
		} finally {
			session.close();
		}
	}

	public static List<CommondWords> selectByUserId(String userId) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(CommondWords.class.getName()
					+ ".selectByUserId", userId);
		} finally {
			session.close();
		}
	}
	
	public static List<CommondWords> selectByTypeId(String typeId) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(CommondWords.class.getName()
					+ ".selectByTypeId", typeId);
		} finally {
			session.close();
		}
	}

	public static CommondWords get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			CommondWords t = session.selectOne(CommondWords.class.getName()
					+ ".get", id);
			return t;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof CommondWords) {
			CommondWords words = (CommondWords) obj;
			if (words.getWordContent().equals(this.getWordContent())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		GregorianCalendar date = new GregorianCalendar();
		return (int) date.getTime().getTime();
	}
}
