package org.szcloud.framework.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface QueryChannelService extends Serializable {

	/**
	 * 查询记录条数
	 * @param clazz
	 * @param queryStr
	 * @param params
	 * @return
	 */
	public long queryResultSize(Class<?> clazz,String queryStr, Map<String, Object> params);
	
	/**
	 * 查询单条记录
	 */
	public <T> T querySingleResult(Class<?> clazz,String queryStr, Map<String, Object> params);
	
	/**
	 * 通过Map分页查询多条记录
	 * @param clazz
	 * @param queryStr
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public <T> PageList<T> queryPagedResult(Class<?> clazz,String queryStr, Map<String, Object> params, 
			int currentPage, int pageSize,String sortString);
	
	/**
	 * 查询多条记录
	 */
	public <T> List<T> queryResult(Class<T> clazz,String queryStr, Map<String, Object> params);
	
	/**
	 * 分页查询
	 */
	public <T> PageList<T> eqQueryPagedResult(Class<?> clazz,Object entity, 
			int currentPage, int pageSize,String sortString);
	
	/**
	 * 通过BaseExample分页查询多条记录(没有传查询sql)
	 */
	public <T> PageList<T> selectPagedByExample(Class<?> clazz,BaseExample example,
			int currentPage, int pageSize,String sortString);
	
	/**
	 * 
	 * @param countId 查询记录条数的select标签的ID （mybatis中xml）
	 * @param selectId 查询记录的select标签的ID （mybatis中xml）
	 * @param clazz
	 * @param example
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public <T> PageList<T> selectPagedByExample(String countId, String selectId, Class<?> clazz,BaseExample example,
			int currentPage, int pageSize,String sortString);
	
	/**
	 * 返回为hashMap<String,Object> 带分页 可联合查询
	 * @param clazz
	 * @param queryStr
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public <T> PageList<T> selectMapByPage(Class<?> clazz,String queryStr,Map<String,Object> params,
			int currentPage,int pageSize,String sortString);
	
	/**
	 * 执行sql
	 * @param clazz
	 * @param method
	 * @param params
	 * @return
	 */
	public boolean excuteMethod(Class<?> clazz,String method,Map<String, Object> params);
	
	/**
	 * 条件查询   可联合查询
	 * @param clazz
	 * @param queryStr
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> selectMap(Class<?> clazz,String queryStr,Map<String,Object> params);
		
	/**
	 * 查询多条记录,得到List
	 */
	public <T> List<T> queryPagedResult(Class<?> clazz,String queryStr, Map<String, Object> params);

}
	
