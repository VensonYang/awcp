package org.szcloud.framework.unit.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.unit.core.domain.PunUserBaseInfo;
import org.szcloud.framework.unit.vo.PunUserBaseInfoVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface PunUserBaseInfoService {
	
	/**
	 * 新增，更新用户，包括与角色的关系
	 * @param vo
	 */
	public Long addOrUpdateUser(PunUserBaseInfoVO vo);
	
	/**
	 * 新增，更新用户，包括与角色的关系
	 * @param vo
	 */
	public void addOrUpdateUsers(PunUserBaseInfoVO vo,long sysGroupID);
	
	/**
	 * 新增or更新用户信息，不包括角色
	 * 修改密码
	 * @param vo
	 */
	public void updateUser(PunUserBaseInfoVO vo);
	
	/**
	 * 通过用户ID查找用户
	 * @param id
	 * @return
	 */
	public PunUserBaseInfoVO findById(Long id);
	
	/**
	 * 根据用户ID，删除用户
	 * @param id
	 * @return
	 */
	public String delete(Long id);
	
	/**
	 * 删除关联
	 * @param user
	 */
	public void removeRelations(PunUserBaseInfo user);
	
	/**
	 * 等值查询用户，不分页
	 * @param queryStr mybatis中xml文件中select节点的id，一般使用 eqQueryList
	 * @param params 查询条件 key为 PunUserBaseInfoVO 属性名称 value为查询值
	 * @return
	 */
	public List<PunUserBaseInfoVO> queryResult(String queryStr,Map<String, Object> params);
	
	/**
	 * 等值查询用户，分页
	 * 
	 * @param queryStr mybatis中xml文件中select节点的id，一般使用 eqQueryList
	 * @param params	查询条件 key为 PunUserBaseInfoVO 属性名称 value为查询值
	 * @param currentPage 当前第几页
	 * @param pageSize	每页多少条记录
	 * @param sortString	排序 如
	 * @return
	 */
	public PageList<PunUserBaseInfoVO> queryPagedResult(String queryStr,
			Map<String, Object> params, int currentPage, int pageSize,
			String sortString);
	
	/**
	 * 通过身份证号码查找
	 * @param idCardNum
	 * @return
	 */
	public List<PunUserBaseInfoVO> selectByIDCard(String idCardNum);
	
	/**
	 * 根据用户名查找
	 * @param userName
	 * @return
	 */
	public List<PunUserBaseInfoVO> selectByUserName(String userName);
	
	/**
	 * 根据组织机构Id和身份证号码获取唯一的用户信息；
	 * @param groupId
	 * @param userIdCardNumber
	 * @return
	 */
	public PunUserBaseInfoVO getUserBaseInfoByGroupIdAndCardNumber(Long groupId, String userIdCardNumber);
	
	/**
	 * 条件查询，分页显示，可排序
	 * @param example
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 */
	public PageList<PunUserBaseInfoVO> selectPagedByExample(BaseExample example, int 
			currentPage, int pageSize, String sortString);

	/**
	 * 用户管理列表
	 * 
	 *
	 * @方法名称：selectByExample_UserList()
	 * @作者：huangmin
	 * @创建日期：2015年7月24日 下午4:49:07
	 *
	 * @param example
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 * PageList<PunUserBaseInfoVO>
	 *
	 * @修改记录（修改时间、作者、原因）：
	 */
	public PageList<PunUserBaseInfoVO> selectByExample_UserList(BaseExample example,
			int currentPage, int pageSize, String sortString);
}
