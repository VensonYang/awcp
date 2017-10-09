package org.szcloud.framework.core.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EntityRepository的Mybatis实现版本
 * 
 * @author caoyong
 * 
 */
@Service(value = "repository")
public class EntityRepositoryMybatis implements EntityRepository {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public <T extends Entity> T save(T entity) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			if(entity.getId() != null) {
				session.update(entity.getClass().getName() + ".update",entity);				
			} else  {
				session.insert(entity.getClass().getName() + ".insert", entity);
			}
			session.commit();
		} finally {
			session.close();
		}
		return entity;
	}

	public void remove(Entity entity) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.delete(entity.getClass().getName() + ".remove", entity);
			session.commit();
		} finally {
			session.clearCache();
			session.close();
		}
	}

	public <T extends Entity> boolean exists(Class<T> clazz, Serializable id) {
		T t = get(clazz, id);
		if (t != null)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T get(Class<T> clazz, Serializable id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			T t = (T) session.selectOne(clazz.getName() + ".get", id);
			return t;
		} finally {
			session.close();
		}
	}

	public <T extends Entity> T load(Class<T> clazz, Serializable id) {
		return get(clazz, id);
	}

	public <T extends Entity> T getUnmodified(Class<T> clazz, T entity) {
		throw new UnsupportedOperationException();
	}

	public <T extends Entity> List<T> findAll(Class<T> clazz) {
		SqlSession session = sqlSessionFactory.openSession();
		List<T> lists = null;
		try {
			lists = session.selectList(clazz.getName() + ".getAll", null);
		} finally {
			session.close();
		}
		return lists;
	}

	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(Class<T> clazz, Object[] params,
			Class<T> resultClass) {
		SqlSession session = sqlSessionFactory.openSession();
		T t = null;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("params", params);
		try {
			t = (T) session.selectOne(clazz.getName() + ".get", paramsMap);
		} finally {
			session.close();
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(Class<T> clazz, Map<String, Object> params,
			Class<T> resultClass) {
		SqlSession session = sqlSessionFactory.openSession();
		T t = null;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("params", params);
		try {
			t = (T) session.selectOne(clazz.getName() + ".get", paramsMap);
		} finally {
			session.close();
		}
		return t;
	}

	public void flush() {
		throw new UnsupportedOperationException();
	}

	public void refresh(Entity entity) {
		throw new UnsupportedOperationException();
	}

	public void clear() {

	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public boolean existed() throws Exception {
		return false;
	}

	public boolean notExisted() throws Exception {
		return false;
	}

	public <T extends Entity> List<T> findByProperty(Class<T> clazz,String propertyName, Object propertyValue) {
		return null;
	}

	public <T extends Entity> List<T> findByProperties(Class<T> clazz,NamedParameters properties) {
		return null;
	}

	public <T extends Entity> List<T> selectByExample(Class<T> clazz,BaseExample example) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			return session.selectList(clazz.getName() + ".selectByExample",example);
		} finally {
			session.close();
		}
	}

	public String getQueryStringOfNamedQuery(String queryName) {
		return null;
	}
	
	public void excuteSql(String sql) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("sql", sql);
			session.update("executeSql",map);
			session.commit();
		}finally {
		  session.close();
		}
	}

	public void executeUpdate(String queryString, Map<String, Object> params,Class<?> clazz) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.update(clazz.getName()+"."+queryString, params);
			session.commit();
		} finally {
			session.close();
		}
	}
	
	public int executeUpdateForInt(String queryString, Map<String, Object> params,Class<?> clazz) {
		SqlSession session = sqlSessionFactory.openSession();
		int i = 0;
		try {
			i = session.update(clazz.getName()+"."+queryString, params);
			session.commit();
		} finally {
			session.close();
		}
		return i;
	}

	public void removeByFK(Class<?> clazz,String queryStr,long fkId) {
		SqlSession session = sqlSessionFactory.openSession();	
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("fkId", fkId);
			session.delete(clazz.getName()+"."+queryStr, map);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.clearCache();
			session.close();
		}
	}

	@Override
	public void removeByExample(Class<?> clazz, BaseExample example) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.delete(clazz.getName() + ".removeByExample", example);
			session.commit();
		} finally {
			session.clearCache();
			session.close();
		}
	}
}
