package org.szcloud.framework.unit.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.unit.vo.PunGroupVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface PunGroupService {
	
	public void addOrUpdate(PunGroupVO vo);
	
	public PunGroupVO findById(Long id);
	
	public List<PunGroupVO> findAll();
	
	public String delete(Long id);
	
	public List<PunGroupVO> queryResult(String queryStr,Map<String, Object> params);
	
	public PageList<PunGroupVO> queryPagedResult(String queryStr,
			Map<String, Object> params, int currentPage, int pageSize,
			String sortString);
	
	/**
	 * 根据根组织的组织机构代码获取组织信息；
	 * @param orgCode
	 * @return
	 */
	public PunGroupVO getRootGroupByOrgCode(String orgCode);
	
	/**
	 * 
	* @Title: cascadeDelete 
	* @Description: 批量删除，根据模糊条件pid
	* @author ljw 
	* @param @param pid    
	* @return void
	* @throws
	 */
	void cascadeDelete(String pid); 
	
	/**
	 * 根据PID获取子孙group列表；
	 * @param pid
	 * @param groupType
	 * @return
	 */
	public List<PunGroupVO> getGroupListByPid(String pid);
	
	/**
	 * 根据某个PID查询下面所有的ID
	 * @param pid
	 * @return
	 */
	public List<PunGroupVO> getGroupListByPids(String pid);
	
	/**
	 * 模糊查询
	 * @param baseExample
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<PunGroupVO> selectPagedByExample(BaseExample baseExample,int currentPage, int pageSize,String sortString);
	
	/**
	 * 模糊查询(带关联表)
	 * @param baseExample
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<PunGroupVO> selectPagedByExample2(BaseExample baseExample,int currentPage, int pageSize,String sortString);
	
	/**
	 * 查询指定用户的所有组
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return null if userId is null
	 */
	public PageList<PunGroupVO> queryGroupByUserId(Long userId,int currentPage, int pageSize,String sortString);
	
}
