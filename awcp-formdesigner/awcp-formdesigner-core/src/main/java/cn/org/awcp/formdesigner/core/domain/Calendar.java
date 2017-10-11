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

public class Calendar implements Serializable {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(Calendar.class);

	private static final long serialVersionUID = -3194804753546276914L;

	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getRepository() {
		if (sqlSessionFactory == null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return Calendar.sqlSessionFactory;
	}

	/**
	 * uuid 唯一标识
	 */
	private String id;
	/**
	 * 编码
	 */
	private String title;
	/**
	 * 名称
	 */
	private String description;
	/**
	 * 内容
	 */
	private String calendar;
	/**
	 * 描述
	 */
	private boolean allDay;
	/**
	 * 逻辑存在状态 --是否被删除 1表示删除状态，0表示存储状态
	 */
	private Date start;
	/**
	 * 所属页面ID
	 */
	private Date end;
	/**
	 * 所属按钮组
	 */
	private Long userId;

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public static void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		Calendar.sqlSessionFactory = sqlSessionFactory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	public boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void main(String[] args) {
		JSONObject o = JSONObject.parseObject(null);
		logger.debug(o + "");
	}

	public String save() {
		SqlSession session = getRepository().openSession();
		try {
			if (StringUtils.isNotBlank(this.id)) {
				session.update(Calendar.class.getName() + ".update", this);
			} else {
				this.setId(UUID.randomUUID().toString());
				session.insert(Calendar.class.getName() + ".insert", this);
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
			session.delete(Calendar.class.getName() + ".remove", this);
			session.commit();
		} finally {
			session.clearCache();
			session.close();
		}
	}

	public static List<Calendar> selectByExample(BaseExample example) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(Calendar.class.getName() + ".selectByExample", example);
		} finally {
			session.close();
		}
	}

	public static Calendar get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			Calendar t = session.selectOne(Calendar.class.getName() + ".get", id);
			return t;
		} finally {
			session.close();
		}
	}

}
