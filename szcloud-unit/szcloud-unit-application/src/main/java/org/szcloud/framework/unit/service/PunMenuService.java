package org.szcloud.framework.unit.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.unit.vo.PunMenuVO;
import org.szcloud.framework.unit.vo.PunRoleInfoVO;
import org.szcloud.framework.unit.vo.PunSystemVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface PunMenuService {

	void addOrUpdate(PunMenuVO vo);

	PunMenuVO findById(Long id);

	List<PunMenuVO> findAll();

	String delete(Long id);

	List<PunMenuVO> queryResult(String queryStr, Map<String, Object> params);

	// 级联删除
	public String deleteCascade(Long id);

	PageList<PunMenuVO> queryPagedResult(String queryStr,
			Map<String, Object> params, int currentPage, int pageSize,
			String sortString);

	/**
	 * 根据MemberShip获取用户资源列表;
	 * 
	 * @param vo
	 * @return
	 */
	public List<PunMenuVO> getPunMenuUserRoleAndSys(Long userId, Long roleId,
			Long sysId);

	/**
	 * 根据系统ID菜单列表;
	 * 
	 * @param svo
	 * @return
	 */
	public List<PunMenuVO> getPunMenuBySys(PunSystemVO svo);

	/**
	 * 根据ID列表获取菜单详细信息；
	 * 
	 * @param menuIds
	 * @return
	 */
	public List<PunMenuVO> getPunMenuVOByMenuIds(List<Long> menuIds);

	List<PunMenuVO> selectByExample(BaseExample example);

	/**
	 * 根据sysId和whichEnd获取菜单信息
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	public List<PunMenuVO> getPunMenuByEnd(Map<String, Object> params);

	/**
	 * 根据PID获取某一结点的子孙菜单信息；
	 * 
	 * @param pid
	 * @return
	 */
	public List<PunMenuVO> getPosterityPunMenuByPid(String pid);

	/**
	 * 根据PID获取某一结点的子菜单信息；
	 * 
	 * @param pid
	 * @return
	 */
	public List<PunMenuVO> getChildrenPunMenuByPid(String pid);

	/**
	 * 根据角色RoleId和系统SysId获取菜单列表；
	 * 
	 * @param vo
	 * @param svo
	 * @return
	 */
	public List<PunMenuVO> getByRoleAndSys(PunRoleInfoVO vo, PunSystemVO svo);
	
	/**
	 * 条件查询,分页显示
	 * @param baseExample
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<PunMenuVO> selectPagedByExample(BaseExample baseExample,int currentPage, int pageSize,String sortString);
	
}
