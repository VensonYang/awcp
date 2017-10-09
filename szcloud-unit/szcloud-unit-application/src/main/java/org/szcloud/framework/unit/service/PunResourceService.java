package org.szcloud.framework.unit.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.unit.vo.PunResourceVO;

public interface PunResourceService {

	/**
	 * 更新或者增加资源；
	 * @param vo
	 */
	void addOrUpdate(PunResourceVO vo);
	
	/**
	 * 根据资源ID查找资源；
	 * @param id
	 * @return
	 */
	PunResourceVO findById(Long id);
	
	/**
	 * 查询所有资源;
	 * @return
	 */
	List<PunResourceVO> findAll();
	
	/**
	 * 根据ID删除一个资源；
	 * @param id
	 * @return
	 */
	String delete(Long id);	
	
	/**
	 * 根据资源和type删除
	 * 
	 * @return
	 */
	public void removeByRelateResoAndType(String relateResoId, String type);
	
	/**
	 * 根据查询条件查询资源清单；
	 * @param queryStr
	 * @param params
	 * @return
	 */
	
	List<PunResourceVO> queryResult(String queryStr,Map<String, Object> params);
	
	/**
	 * 查询按钮，输出格式为Map<relatResoId,PunResourceVO>
	 * @param queryStr
	 * @param params
	 * @return
	 */
	Map<String, PunResourceVO> queryButtonMap(String queryStr,Map<String, Object> params);
	
	/**
	 * 根据查询条件查询所有符合条件的资源ID
	 * @param queryStr
	 * @param params
	 * @return
	 */
	List<String> queryResoIds(String queryStr,Map<String, Object> params);
	
	/**
	 * 根据ID列表获取资源清单；
	 * @param resourceIds
	 * @return
	 */
	List<PunResourceVO> getPunResourceByIds(List<Long> resourceIds);
	
	/**
	 * 根据relate ID列表获取资源清单；
	 * @param relateResourceId
	 * @return
	 */
	public List<PunResourceVO> getResourceListByRelateIds(List<Long> relateResourceId, int resourceType);
	
	public Long getResourceIdByRelateId(String relateId, String resourceType);
	
	public PunResourceVO getResourceByRelateId(String relateId, String resourceType);
}
