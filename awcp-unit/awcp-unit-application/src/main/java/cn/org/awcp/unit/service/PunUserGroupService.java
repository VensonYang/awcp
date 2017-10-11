package cn.org.awcp.unit.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.unit.vo.PunUserBaseInfoVO;
import cn.org.awcp.unit.vo.PunUserGroupVO;

public interface PunUserGroupService {
	public List<PunUserGroupVO> findAll();
	
	public void remove(PunUserGroupVO vo);
	
	public void update(PunUserGroupVO vo,String queryStr);
		
	public void save(PunUserGroupVO vo);
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PunUserGroupVO> selectPagedByExample(String queryStr, Map<String, Object> params, int currentPage, int pageSize,String sortString);
	
	public PunUserGroupVO get(Long id);
	
	public PageList<PunUserBaseInfoVO> queryUserListByGroupId(Long groupId);
	
	public PageList<PunUserBaseInfoVO> queryUserByUserIdAndGroupId(Long groupId,Long userId);
	
	public List<PunUserGroupVO> queryResult(String queryStr,Map<String, Object> params);
	
	/*
	 * 根据UserId找到其所在组织的Manager
	 */
	public List<PunUserGroupVO> queryDirectManager(Long userId);
	
	/*
	 * 根据userId找到其上级组织的Manager
	 */
	public List<PunUserGroupVO> queryParentManager(Long userId);
	
}