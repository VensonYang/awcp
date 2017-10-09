package  org.szcloud.framework.unit.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import org.szcloud.framework.unit.vo.PunRoleInfoVO;
import org.szcloud.framework.unit.vo.PunUserRoleVO;

public interface PunUserRoleService {
	
	/**
	 * 存储或更新
	 * @param vo
	 */
	public void addOrUpdate(PunUserRoleVO vo);
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<PunUserRoleVO> findAll();
	
	/**
	 * 删除
	 * @param vo
	 */
	public void remove(PunUserRoleVO vo);
	
	 /**
	  * 更新
	  * @param vo
	  * @param queryStr
	  */
	public void update(PunUserRoleVO vo,String queryStr);
	
	/**
	 * 根据条件查询
	 * @param queryStr mapper.xml中的id
	 * @param params  参数
	 * @return
	 */
	public List<PunUserRoleVO> queryResult(String queryStr,Map<String, Object> params);
	
	/**
	 * 多功能查询(模糊等)
	 * @param baseExample
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<PunUserRoleVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize,String sortString);
	
	public List<PunUserRoleVO> selectByExample(PunUserRoleVO vo);
		
	public void save(PunUserRoleVO vo);
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PunUserRoleVO> queryPagedResult(Map<String, Object> params, int currentPage, int pageSize,String sortString);
	
	public PunUserRoleVO get(Long id);
	
	/**
	 * 根据角色Id和用户ID删除
	 * @param roleId 角色ID
	 * @param boxs 用户ID的列表
	 * @return
	 */
	public boolean deleteByRoleIdAndUserId(Long roleId,Long[] boxs) throws MRTException;
	
	/**
	 * 根据用户ID和角色ID列表，删除用户与角色的关系
	 * @param userId
	 * @throws MRTException
	 */
	
	public void deletebyUserIdAndRoleIds(Long userId,List<PunRoleInfoVO> roles) throws MRTException;
	/**
	 * 
	 * 角色与人员之间关联
	 *
	 * @方法名称：excuteRoleAndUser()
	 * @作者：huangmin
	 * @创建日期：2015年8月4日 上午10:40:55
	 *
	 * @param roleId 角色编号
	 * @param userIds 用户编号
	 * @return
	 * boolean
	 *
	 * @修改记录（修改时间、作者、原因）：
	 */
	public boolean excuteRoleAndUser(String roleId,String userIds);
	
}