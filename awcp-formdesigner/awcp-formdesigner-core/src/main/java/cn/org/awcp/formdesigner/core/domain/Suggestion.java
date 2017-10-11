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

public class Suggestion implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String dept;
	private String deptName;
	private Date date;
	private Date sendTime;// 发送时间
	private String conment;
	private String businessid;
	private String person;
	private String personName;
	private int flag;
	private String link;
	private String linkName;
	private int order;
	private String type;
	private String status;
	private String isLeader;// 是否属于领导
	private String deadline;// 期限

	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getRepository() {
		if (sqlSessionFactory == null)
			sqlSessionFactory = Springfactory.getBean("sqlSessionFactory");
		return Suggestion.sqlSessionFactory;
	}

	public String save() {
		SqlSession session = getRepository().openSession();
		try {
			if (StringUtils.isNotBlank(this.getId())) {
				session.update(Suggestion.class.getName() + ".update", this);
			} else {
				this.setId(UUID.randomUUID().toString());
				session.insert(Suggestion.class.getName() + ".insert", this);
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
			session.delete(Suggestion.class.getName() + ".remove", this);
			session.commit();
		} finally {
			session.clearCache();
			session.close();
		}
	}

	public static List<Suggestion> selectByExample(BaseExample example) {
		SqlSession session = getRepository().openSession();
		try {
			return session.selectList(Suggestion.class.getName() + ".selectByExample", example);
		} finally {
			session.close();
		}
	}

	public static Suggestion get(Serializable id) {
		SqlSession session = getRepository().openSession();
		try {
			Suggestion t = session.selectOne(Suggestion.class.getName() + ".get", id);
			return t;
		} finally {
			session.close();
		}
	}

	public String getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(String isLeader) {
		this.isLeader = isLeader;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getConment() {
		return conment;
	}

	public void setConment(String conment) {
		this.conment = conment;
	}

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
