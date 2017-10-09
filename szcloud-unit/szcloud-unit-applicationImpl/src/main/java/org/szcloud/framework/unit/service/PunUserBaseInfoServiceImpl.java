package org.szcloud.framework.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.core.utils.SessionUtils;
import org.szcloud.framework.core.utils.constants.SessionContants;
import org.szcloud.framework.metadesigner.application.MetaModelService;
import org.szcloud.framework.unit.core.domain.PunUserBaseInfo;
import org.szcloud.framework.unit.vo.PunRoleInfoVO;
import org.szcloud.framework.unit.vo.PunSystemVO;
import org.szcloud.framework.unit.vo.PunUserBaseInfoVO;
import org.szcloud.framework.unit.vo.PunUserRoleVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
@Transactional
public class PunUserBaseInfoServiceImpl implements PunUserBaseInfoService{
	
	@Autowired
	private QueryChannelService queryChannel;
	
	@Autowired
	@Qualifier("workflowSyncServiceImpl")
	private WorkflowSyncService workflowSyncService;
	
	@Autowired
	@Qualifier("punUserRoleServiceImpl")
	private PunUserRoleService userRoleService;
	
	@Autowired
	@Qualifier("punRoleInfoServiceImpl")
	private PunRoleInfoService roleService;

	@Autowired
	private MetaModelService metaModelServiceImpl;
	
	private static final Long USER_GROUP_TYPE = new Long(1);
	
	/**
	 * 
	 * @Title: addOrUpdateUser 
	 * @Description: 用户增加或更新，包括角色
	 * @author ljw 
	 * @param  userVo
	 * @throws MRTException    
	 * @return void
	 * @throws
	 */
	public Long addOrUpdateUser(PunUserBaseInfoVO userVo) throws MRTException{
		PunUserBaseInfo user = BeanUtils.getNewInstance(userVo, PunUserBaseInfo.class);
		if(null != userVo.getUserId()){
			user.setId(userVo.getUserId());
		}
		user.save();
		//新增或者删除用户的时候将其用户角色也重置了，先删除[当前用户登录的系统]中的用户拥有角色，然后再插入
		//新增用户和角色的关系
		//1、获取新增的用户ID
		//2、根据用户ID和系统ID查找当前用户与角色的关系，并删除
		//3、新增用户与所选择用户角色的关系
		Long id = user.getId();
		List<Long> roleIds = userVo.getRoleList();
		PunSystemVO systemVO = (PunSystemVO)SessionUtils.getObjectFromSession(SessionContants.CURRENT_SYSTEM);	
		if (null != systemVO) {
			Map<String, Object> roleParams = new HashMap<String, Object>();
			roleParams.put("sysId", systemVO.getSysId());
			List<PunRoleInfoVO> roleInfoVOs = roleService.queryResult("eqQueryList", roleParams);
			userRoleService.deletebyUserIdAndRoleIds(id, roleInfoVOs);
		}
		
		if (null != roleIds && roleIds.size()>0) {			
			for (Long roleId : roleIds) {
				PunUserRoleVO userRoleVO = new PunUserRoleVO();
				userRoleVO.setRoleId(roleId);
				userRoleVO.setUserId(id);
				userRoleService.addOrUpdate(userRoleVO);
			}
		}
		userVo.setUserId(user.getId());
		workflowSyncService.saveGroup(user.getId(), user.getUserName(), USER_GROUP_TYPE);
		return id;
	}
	
	/**
	 * 
	 * @Title: addOrUpdateUser 
	 * @Description: 用户增加或更新，包括角色
	 * @author ljw 
	 * @param  userVo
	 * @throws MRTException    
	 * @return void
	 * @throws
	 */
	public void addOrUpdateUsers(PunUserBaseInfoVO userVo,long sysGroupID) throws MRTException{
		PunUserBaseInfo user = BeanUtils.getNewInstance(userVo, PunUserBaseInfo.class);
		if(null != userVo.getUserId()){
			user.setId(userVo.getUserId());
		}
		user.save();
		// 新增或者删除用户的时候将其用户角色也重置了，先删除[当前用户登录的系统]中的用户拥有角色，然后再插入
		//新增用户和角色的关系
		//1、获取新增的用户ID
		//2、根据用户ID和系统ID查找当前用户与角色的关系，并删除
		//3、新增用户与所选择用户角色的关系
		Long id = user.getId();
		List<Long> roleIds = userVo.getRoleList();
		PunSystemVO systemVO = (PunSystemVO)SessionUtils.getObjectFromSession(SessionContants.CURRENT_SYSTEM);		
		if (null != systemVO) {
			Map<String, Object> roleParams = new HashMap<String, Object>();
			roleParams.put("sysId", systemVO.getSysId());
			List<PunRoleInfoVO> roleInfoVOs = roleService.queryResult("eqQueryList", roleParams);
			userRoleService.deletebyUserIdAndRoleIds(id, roleInfoVOs);
		}
		
		if (null != roleIds && roleIds.size()>0) {			
			for (Long roleId : roleIds) {
				PunUserRoleVO userRoleVO = new PunUserRoleVO();
				userRoleVO.setRoleId(roleId);
				userRoleVO.setUserId(id);
				userRoleService.addOrUpdate(userRoleVO);
			}
		}
		userVo.setUserId(user.getId());
		workflowSyncService.saveGroup(user.getId(), user.getUserName(), USER_GROUP_TYPE);	
		StringBuffer sb=new StringBuffer("insert into p_un_user_group(User_ID,Group_ID,POSITION_ID,IS_Manager) values(");
		sb.append(id+",");
		sb.append(sysGroupID+",");
		sb.append("6,");
		sb.append("null)");
		metaModelServiceImpl.excuteSql(sb.toString());
	}
	
	@Override
	public void updateUser(PunUserBaseInfoVO userVo) throws MRTException{
		PunUserBaseInfo user = BeanUtils.getNewInstance(userVo, PunUserBaseInfo.class);
		if(null != userVo.getUserId()){
			user.setId(userVo.getUserId());
		}
		user.save();	
	}

	/**
	 * 
	 * @Title: findById 
	 * @Description: 查找用户，根据Id
	 * @author ljw 
	 * @param  id
	 * @throws MRTException    
 	 * @return PunUserBaseInfoVO
	 * @throws
	 */
	public PunUserBaseInfoVO findById(Long id) throws MRTException{
		PunUserBaseInfo user = PunUserBaseInfo.get(PunUserBaseInfo.class, id);
		return BeanUtils.getNewInstance(user, PunUserBaseInfoVO.class);
	}
	
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 分页查询
	 * @author ljw 
	 * @param  queryStr
	 * @param  params
	 * @param  currentPage
	 * @param  pageSize
	 * @param  sortString
	 * @return PageList<T>
	 * @throws
	 */
	public PageList<PunUserBaseInfoVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {	
		PageList<PunUserBaseInfo> users = queryChannel.queryPagedResult(PunUserBaseInfo.class, queryStr, params, 
				currentPage, pageSize,sortString);
		List<PunUserBaseInfoVO> tmp = new ArrayList<PunUserBaseInfoVO>();
		for (PunUserBaseInfo user : users) {
			tmp.add(BeanUtils.getNewInstance(user, PunUserBaseInfoVO.class));
		}
		PageList<PunUserBaseInfoVO> vos = new PageList<PunUserBaseInfoVO>(tmp, users.getPaginator());
		users.clear();
		return vos;
	}
	
	/**
	 * 
	 * @Title: queryResult 
	 * @Description: 根据查询条件获取组信息
	 * @author ljw 
	 * @param  params
	 * @throws MRTException    
	 * @return List<PunUserBaseInfoVO>
	 * @throws
	 */
	public List<PunUserBaseInfoVO> queryResult(String queryStr,Map<String, Object> params) throws MRTException{
		List<PunUserBaseInfo> users = queryChannel.queryResult(PunUserBaseInfo.class, queryStr, params);
		List<PunUserBaseInfoVO> vos = new ArrayList<PunUserBaseInfoVO>();
		for(PunUserBaseInfo group : users){
			vos.add(BeanUtils.getNewInstance(group, PunUserBaseInfoVO.class));
		}
		users.clear();
		return vos;
	}
	
	
	public PunUserBaseInfoVO getUserBaseInfoByGroupIdAndCardNumber(Long groupId, String userIdCardNumber){
		List<PunUserBaseInfoVO> userList = selectByIDCard(userIdCardNumber);
		for(PunUserBaseInfoVO user : userList){
			if(groupId.equals(user.getGroupId())){
				return user;
			}
		}
		return null;
	}
	
 
	public String delete(Long id) throws MRTException{
		PunUserBaseInfo user = PunUserBaseInfo.get(PunUserBaseInfo.class, id);
		String userName = user.getUserName();
		removeRelations(user);
		user.delete();
		workflowSyncService.removeGroup(id, USER_GROUP_TYPE);
		return userName;
	}
	
	/**
	 * 删除用户与其它表的关联关系；
	 * auth:huangqr
	 * @param user
	 * @throws MRTException
	 */
	public void removeRelations(PunUserBaseInfo user) throws MRTException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getUserId());
		queryChannel.excuteMethod(PunUserBaseInfo.class, "removeUserRole", params);
		queryChannel.excuteMethod(PunUserBaseInfo.class, "removeUserGroup", params);	
	}
	
	/**
	 * 
	 * @Title: selectByExample 
	 * @Description:根据example查询数据 
	 * @author ljw 
	 * @param  example
	 * @return List<PunUserBaseInfoVO>
	 * @throws
	 */
	public List<PunUserBaseInfoVO> selectByIDCard(String idCardNum){
		BaseExample example = new BaseExample();
		example.createCriteria().andEqualTo("USER_ID_CARD_NUMBER", idCardNum);
		List<PunUserBaseInfo> result = PunUserBaseInfo.selectByExample(PunUserBaseInfo.class, example);
		List<PunUserBaseInfoVO> resultVo = new ArrayList<PunUserBaseInfoVO>();
		for(PunUserBaseInfo mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunUserBaseInfoVO.class));
		}
		result.clear();
		return resultVo;
	}
	
	/**
	 * 
	 * @Title: selectByExample 
	 * @Description:根据example查询数据 
	 * @author ljw 
	 * @param  example
	 * @return List<PunUserBaseInfoVO>
	 * @throws
	 */
	public List<PunUserBaseInfoVO> selectByUserName(String userName){
		BaseExample example = new BaseExample();
		example.createCriteria().andEqualTo("USER_NAME", userName);
		List<PunUserBaseInfo> result = PunUserBaseInfo.selectByExample(PunUserBaseInfo.class, example);
		List<PunUserBaseInfoVO> resultVo = new ArrayList<PunUserBaseInfoVO>();
		for(PunUserBaseInfo mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunUserBaseInfoVO.class));
		}
		result.clear();
		return resultVo;
	}

	@Override
	public PageList<PunUserBaseInfoVO> selectPagedByExample(BaseExample example, 
			int currentPage, int pageSize,String sortString) {
		PageList<PunUserBaseInfo> list = queryChannel.selectPagedByExample(PunUserBaseInfo.class, example, currentPage, pageSize, sortString);
		PageList<PunUserBaseInfoVO> vos = new PageList<PunUserBaseInfoVO>(list.getPaginator());
		for (PunUserBaseInfo dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, PunUserBaseInfoVO.class));
		}
		list.clear();
		return vos;
	}
	
	/**
	 * 用户管理列表
	 * @方法名称：selectByExample_UserList()
	 * @作者：huangmin
	 * @创建日期：2015年7月24日 下午4:49:07
	 * @param example
	 * @param currentPage
	 * @param pageSize
	 * @param sortString
	 * @return
	 * PageList<PunUserBaseInfoVO>
	 *
	 * @修改记录（修改时间、作者、原因）：
	 */
	@Override
	public PageList<PunUserBaseInfoVO> selectByExample_UserList(BaseExample example, int currentPage, int pageSize,String sortString) {
		PageList<PunUserBaseInfo> list = queryChannel.selectPagedByExample("queryCountByExample_UserList", "selectByExample_UserList_Order", PunUserBaseInfo.class, example, currentPage, pageSize, sortString);
		PageList<PunUserBaseInfoVO> vos = new PageList<PunUserBaseInfoVO>(list.getPaginator());
		for (PunUserBaseInfo dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, PunUserBaseInfoVO.class));
		}
		list.clear();
		return vos;
	}
	
}	


