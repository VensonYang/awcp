package org.szcloud.framework.unit.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.unit.vo.PunMenuVO;
import org.szcloud.framework.unit.vo.PunRoleAccessVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface PunRoleAccessService {
	
	public void addOrUpdateRole(PunRoleAccessVO vo);
	
	public PunRoleAccessVO findById(Long id);
	
	public List<PunRoleAccessVO> findAll();
	
	public String delete(Long id);
	
	public PageList<PunRoleAccessVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString);
	
	/**
	 * 
	 * @Title: batchSave 
	 * @Description: 批量保存或更新
	 * @author ljw 
	 * @param  vo
	 * @return String
	 * @throws
	 */
	public String batchSave(List<PunRoleAccessVO> list);
	
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 查询列表
	 * @author ljw 
	 * @param  queryStr
	 * @param  params
	 * @return List<PunGroupVO>
	 * @throws
	 */
	public List<PunRoleAccessVO> queryResult(String queryStr,Map<String, Object> params);

	/**
	 * @Description :根据VO样例查询PunRoleAccessVO列表；
	 * @param praVO
	 * @return
	 */
	public List<PunRoleAccessVO> queryResultByExample(PunRoleAccessVO praVO);
	
	/**
	 * 根据RoleId, MenuId对菜单结点赋权限，除对自身结点赋权限外，需要对其祖先结点和子孙结点赋权限；
	 * @param roleId
	 * @param menuId
	 * @param operType
	 */
	public void assignMenuAccessRight(Long roleId, Long menuId, Long operType);
	

	/**
	 * 根据RoleId, MenuId删除角色对菜单结点赋权限，除删除自身结点赋权限外，还需删除子孙结点赋权限；
	 * @param roleId
	 * @param menuId
	 * @param operType
	 */
	public void removeMenuAccessRight(Long roleId, Long menuId);
	
	/**
	 * 根据RoleId, resouId对相关资源删除权限
	 * @param roleId
	 * @param menuId
	 * @param operType
	 */
	public void resoAuthorDel(Map<String, Object> params);
	
	/**
	 * 根据RoleId获取所有的菜单列信息；
	 * @param roleId
	 * @return
	 */
	public List<PunMenuVO> getRoleAccessMenuVOByRoleId(Long roleId);
	
	/**
	 * 查询
	 * @param baseExample
	 * @return
	 */
	public List<PunRoleAccessVO> selectByExample(BaseExample baseExample);
}
