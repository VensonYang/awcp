package org.szcloud.framework.core.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

/**
 * 查询通道
 * @author yqtao
 *
 */
@Service(value="queryChannel")
public class QueryChannelServiceMybatis implements QueryChannelService {

	private static final long serialVersionUID = -7717195132438268067L;
	
    @Autowired
	private SqlSessionFactory sqlSessionFactory;	//mybatis SqlSessionFactory

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * 查询记录条数
	 */
	public long queryResultSize(Class<?> clazz,String queryStr, Map<String, Object> params) {
		SqlSession session = sqlSessionFactory.openSession();
		long size = 0;
		try {
			size = session.selectList(clazz.getName()+"."+queryStr, params).size();
		} finally {
		    session.close();
		}
		return size;
	}
	
	/**
	 * 查询单条记录
	 */
	@SuppressWarnings("unchecked")
	public <T> T querySingleResult(Class<?> clazz,String queryStr, Map<String, Object> params) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			T t = (T)session.selectOne(clazz.getName()+"."+queryStr, params);
			return t;
		} finally {
		  session.close();
		}
	}
	
	/**
	 * 通过Map分页查询多条记录
	 */
	@SuppressWarnings("unchecked")
	public <T> PageList<T> queryPagedResult(Class<?> clazz,String queryStr, Map<String, Object> params, 
			int currentPage, int pageSize,String sortString){
		SqlSession session = sqlSessionFactory.openSession();
		PageBounds pageBounds = new PageBounds(currentPage, pageSize , Order.formString(sortString));  
		try{
			return (PageList<T>)session.selectList(clazz.getName()+"."+queryStr, params, pageBounds);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 查询多条记录
	 */
	public <T> List<T> queryResult(Class<T> clazz,String queryStr, Map<String, Object> params){
		SqlSession session = sqlSessionFactory.openSession();
		try{
			return session.selectList(clazz.getName()+"."+queryStr, params);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	public <T> PageList<T> eqQueryPagedResult(Class<?> clazz,Object entity, int currentPage, int pageSize,String sortString){
		SqlSession session = sqlSessionFactory.openSession();
		PageBounds pageBounds = new PageBounds(currentPage, pageSize , Order.formString(sortString));  
		try{
			return (PageList<T>)session.selectList(clazz.getName()+".eqQueryList", entity,pageBounds );
		}finally{
			session.close();
		}
	}
	
	/**
	 * 通过BaseExample分页查询多条记录(没有传查询sql)
	 */
	public <T> PageList<T> selectPagedByExample(Class<?> clazz,BaseExample example,
			int currentPage, int pageSize,String sortString) {
		SqlSession session = sqlSessionFactory.openSession();
		example.orderByClause = sortString;
		example.start = (currentPage-1) * pageSize;
		example.limit = pageSize;
		try {
			Integer count = session.selectOne(clazz.getName() + ".queryCountByExample",example);
			List<T> list = session.selectList(clazz.getName() + ".selectByExample",example);
			Paginator paginator = new Paginator(currentPage,pageSize,count);
			PageList<T> page = new PageList<T>(list,paginator);
			return page;
		} finally {
			session.close();
		}
	}	
	
	/**
	 * 通过BaseExample分页查询多条记录(传查询sql)
	 */
	public <T> PageList<T> selectPagedByExample(String countId, String selectId, Class<?> clazz,BaseExample example,
			int currentPage, int pageSize,String sortString) {
		SqlSession session = sqlSessionFactory.openSession();
		example.orderByClause = sortString;
		example.start = (currentPage-1) * pageSize;
		example.limit = pageSize;
		try {
			Integer count = session.selectOne(clazz.getName() + "." + countId,example);
			List<T> list = session.selectList(clazz.getName() + "." + selectId,example);
			Paginator paginator = new Paginator(currentPage,pageSize,count);
			PageList<T> page = new PageList<T>(list,paginator);
			return page;
		} finally {
			session.close();
		}
	}	
	
	/**
	 * 通过Map分页查询多条记录
	 */
	@SuppressWarnings("unchecked")
	public <T> PageList<T> selectMapByPage(Class<?> clazz,String queryStr,Map<String,Object> params,
			int currentPage,int pageSize,String sortString){
		SqlSession session=sqlSessionFactory.openSession();
		PageList<T> pl = null;
		try {
			PageBounds pageBounds = new PageBounds(currentPage, pageSize , Order.formString(sortString));
			pl = (PageList<T>) session.selectList(clazz.getName()+"."+queryStr, params, pageBounds);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return pl;
	}
	
	/**
	 * 执行sql
	 * @param clazz
	 * @param method
	 * @param params
	 * @return
	 */
	public boolean excuteMethod(Class<?> clazz,String method,Map<String, Object> params){
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.update(clazz.getName()+"."+method,params); 
			session.commit(); 
			session.close(); 
			return true;
		}catch(Exception e){ 
			return false;
		}finally {
		  session.close();
		}
	}

	/**
	 * 查询多条记录,得到List<Map>
	 */
	public List<Map<String, Object>> selectMap(Class<?> clazz, String queryStr,Map<String, Object> params) {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String,Object>> m = null;
		try {
			m = session.selectList(clazz.getName()+"."+queryStr, params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return m;
	}
	
	/**
	 * 查询多条记录,得到List
	 */
	public <T> List<T> queryPagedResult(Class<?> clazz,String queryStr, Map<String, Object> params){
		SqlSession session = sqlSessionFactory.openSession();
		PageBounds pageBounds = new PageBounds(1, Integer.MAX_VALUE , Order.formString(""));  
		try{
			return session.selectList(clazz.getName()+"."+queryStr, params, pageBounds);
		}finally{
			session.close();
		}
	}

}
