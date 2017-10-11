package cn.org.awcp.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.unit.core.domain.PunUserGroup;
import cn.org.awcp.unit.service.PunGroupService;
import cn.org.awcp.unit.service.PunRoleInfoService;
import cn.org.awcp.unit.service.PunUserGroupService;
import cn.org.awcp.unit.service.WorkflowSyncService;
import cn.org.awcp.unit.utils.PunGroupUtils;
import cn.org.awcp.unit.vo.PunGroupVO;
import cn.org.awcp.unit.vo.PunUserBaseInfoVO;
import cn.org.awcp.unit.vo.PunUserGroupVO;

@Service(value="punUserGroupServiceImpl")
@Transactional
public class PunUserGroupServiceImpl implements PunUserGroupService{

	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;
	
	@Autowired
	@Qualifier("workflowSyncServiceImpl")
	private WorkflowSyncService workflowSyncService;
	
	@Autowired
	@Qualifier("punGroupServiceImpl")
	private PunGroupService punGroupService;
	
	@Autowired
	@Qualifier("punRoleInfoServiceImpl")
	PunRoleInfoService roleService;
	
	/**
	 * 行政关系类型常量
	 */
	private static final Long WORKFLOW_EXECUTE_RELATION = new Long(1);
	/**
	 * 人员类型
	 */
	private static final Long WORKFLOW_PERSON_TYPE= new Long(1);
	/**
	 * 岗位类型
	 */
	private static final Long WORKFLOW_POSITION_TYPE = new Long(5);
	
	public List<PunUserGroupVO> findAll() {
		List<PunUserGroup> list = PunUserGroup.findAll(PunUserGroup.class);
		List<PunUserGroupVO> ls = new ArrayList<PunUserGroupVO>();
		for(PunUserGroup mm:list){
			ls.add(BeanUtils.getNewInstance(mm, PunUserGroupVO.class));
		}
		return ls;
	}

	public void remove(PunUserGroupVO vo) {
		try {
			Map<String, Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", vo.getUserId());
			paramMap.put("groupId", vo.getGroupId());
			PageList<PunUserGroupVO> voList = selectPagedByExample("queryList",paramMap,0,99999,null);
			//根据userid和groupId删除user－group-position的管理关系
			PunUserGroup mm = BeanUtils.getNewInstance(vo, PunUserGroup.class);			
			PunUserGroup.getRepository().remove(mm);
			//删除人员－岗位的关系;
			ArrayList<Long> positionIdList = new ArrayList<Long>();
			for(int i = 0; i < voList.size(); i++){
				PunUserGroupVO tempVO = (PunUserGroupVO)voList.get(i);
				positionIdList.add(tempVO.getPositionId());
				workflowSyncService.removeRelation(tempVO.getPositionId(), WORKFLOW_POSITION_TYPE,
						tempVO.getUserId(), WORKFLOW_PERSON_TYPE, WORKFLOW_EXECUTE_RELATION);
			}
			
			//如果该人员与组织没有其它的岗位的关系，则增加人员与组织的关系;
			PunGroupVO group = punGroupService.findById(mm.getGroupId());
			Long wkGroupType = PunGroupUtils.getWorkflowGroupType(group.getGroupType());
			workflowSyncService.removeRelation(group.getGroupId(), wkGroupType, vo.getUserId(),
					WORKFLOW_PERSON_TYPE, WORKFLOW_EXECUTE_RELATION);
			//如果组织与岗位没有其它的人员，则删除岗位与组织的关系
			for(int index = 0; index < positionIdList.size(); index++){
				Long positionId = positionIdList.get(index);
				paramMap = new HashMap<String,Object>();
				paramMap.put("positionId", positionId);
				paramMap.put("groupId", mm.getGroupId());
				voList = this.selectPagedByExample("queryList",paramMap,0,99999,null);
				boolean hasOtherPosition = false;
				for(int i = 0; i < voList.size(); i++){
					PunUserGroupVO userGroup = (PunUserGroupVO) voList.get(i);
					if(userGroup.getUserId() != mm.getUserId()){
						hasOtherPosition = true;
						break;
					}
				}
				if(!hasOtherPosition){
					workflowSyncService.removeRelation(group.getGroupId(), wkGroupType, positionId,
							WORKFLOW_POSITION_TYPE, WORKFLOW_EXECUTE_RELATION);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(PunUserGroupVO vo,String queryStr) {
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userGruopId",vo.getUserGruopId());
			map.put("userId",vo.getUserId());
			map.put("groupId",vo.getGroupId());
			map.put("positionId",vo.getPositionId());
			map.put("isManager",vo.getIsManager());
			PunUserGroup.getRepository().executeUpdate(queryStr, map, PunUserGroup.class);
			workflowSyncService.createRelation(vo.getPositionId(), WORKFLOW_POSITION_TYPE, 
					vo.getUserGruopId(),WORKFLOW_PERSON_TYPE , WORKFLOW_EXECUTE_RELATION, vo.getIsManager());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void save(PunUserGroupVO vo) {
		try {
			PunUserGroup mmc = null;
			Map<String, Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", vo.getUserId());
			paramMap.put("groupId", vo.getGroupId());
			paramMap.put("positionId", vo.getPositionId());
			PageList<PunUserGroupVO> voList = selectPagedByExample("queryList",paramMap,0,99999,null);
			if(voList.size() > 0){
				vo.setUserGruopId(((PunUserGroupVO)voList.get(0)).getUserGruopId());
				this.update(vo, "updateSelective");
				mmc=BeanUtils.getNewInstance(vo, PunUserGroup.class);
			}
			else{
				mmc=BeanUtils.getNewInstance(vo, PunUserGroup.class);
				if(vo.getUserGruopId() != null) {
					mmc.setId(vo.getGroupId());
				}
				PunUserGroup.getRepository().save(mmc);
			}
			
			//增加人员－岗位关系;
			workflowSyncService.createRelation(vo.getPositionId(), WORKFLOW_POSITION_TYPE, 
					mmc.getUserId(),WORKFLOW_PERSON_TYPE , WORKFLOW_EXECUTE_RELATION, mmc.getIsManager());
			
			//如果该人员与组织没有其它的岗位的关系，则增加人员与组织的关系;
			PunGroupVO group = punGroupService.findById(mmc.getGroupId());
			Long wkGroupType = PunGroupUtils.getWorkflowGroupType(group.getGroupType());
			paramMap = new HashMap<String,Object>();
			paramMap.put("userId", mmc.getUserId());
			paramMap.put("groupId", mmc.getGroupId());
			voList = this.selectPagedByExample("queryList",paramMap,0,99999,null);
			boolean hasOtherPosition = false;
			for(int i = 0; i < voList.size(); i++){
				PunUserGroupVO userGroup = (PunUserGroupVO) voList.get(i);
				if(userGroup.getPositionId() != null && userGroup.getPositionId().longValue() != mmc.getPositionId().longValue()){
					hasOtherPosition = true;
					break;
				}
			}
			if(!hasOtherPosition){
				workflowSyncService.createRelation(group.getGroupId(), wkGroupType, mmc.getUserId(),
						WORKFLOW_PERSON_TYPE, WORKFLOW_EXECUTE_RELATION, false);
			}
			paramMap = new HashMap<String,Object>();
			paramMap.put("position", mmc.getPositionId());
			paramMap.put("groupId", mmc.getGroupId());
			voList = selectPagedByExample("queryList",paramMap,0,99999,null);
			hasOtherPosition = false;for(int i = 0; i < voList.size(); i++){
				PunUserGroupVO userGroup = (PunUserGroupVO) voList.get(i);
				if(userGroup.getUserId().longValue() != mmc.getUserId().longValue()){
					hasOtherPosition = true;
					break;
				}
			}
			if(!hasOtherPosition){
				workflowSyncService.createRelation(group.getGroupId(), wkGroupType, mmc.getPositionId(),
						WORKFLOW_POSITION_TYPE, WORKFLOW_EXECUTE_RELATION, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PunUserGroupVO> selectPagedByExample(String queryStr, Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {		
		PageList<PunUserGroupVO> resultVO = new PageList<PunUserGroupVO>();
		PageList<PunUserGroup> result = queryChannel.queryPagedResult(PunUserGroup.class, queryStr,params, 
				currentPage, pageSize, sortString);
		for (Object dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PunUserGroupVO.class));			
		}
		result.clear();
		return resultVO;
	}
	
	public PunUserGroupVO get(Long id) {
		try {
			PunUserGroup model =  PunUserGroup.get(PunUserGroup.class, id);
			return BeanUtils.getNewInstance(model, PunUserGroupVO.class);
		} catch (Exception e) {
			throw new RuntimeException("错误信息", e);
		}
	}

	/**
	 * ljw 2014-12-6 修改
	 * 修改分页失败问题
	 */
	@Override
	public PageList<PunUserBaseInfoVO> queryUserListByGroupId(Long groupId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupId", groupId);
		PageList<PunUserGroup> result = queryChannel.queryPagedResult(PunUserGroup.class,"queryUserListByGroupId",map, 0, 99999, null);
		PageList<PunUserBaseInfoVO> userGroupVOs = new PageList<PunUserBaseInfoVO>(result.getPaginator());
		for (Object dd : result) {
			userGroupVOs.add(BeanUtils.getNewInstance(dd, PunUserBaseInfoVO.class));			
		}
		result.clear();
		return userGroupVOs;
	}
	
	@Override
	public PageList<PunUserBaseInfoVO> queryUserByUserIdAndGroupId(Long groupId,Long userId) {
		PageList<PunUserBaseInfoVO> resultVO = new PageList<PunUserBaseInfoVO>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupId", groupId);
		map.put("userId", userId);
		PageList<PunUserGroup> result = queryChannel.queryPagedResult(PunUserGroup.class,"queryUserByUserIdAndGroupId",map, 
				0, 99999, null);
		for (Object dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PunUserBaseInfoVO.class));			
		}
		result.clear();
		return resultVO;
	}

	@Override
	public List<PunUserGroupVO> queryResult(String queryStr,Map<String, Object> params) {
		List<PunUserGroup> members = queryChannel.queryResult(PunUserGroup.class, queryStr, params);
		List<PunUserGroupVO> vos = new ArrayList<PunUserGroupVO>();
		for (PunUserGroup member : members) {
			vos.add(BeanUtils.getNewInstance(member, PunUserGroupVO.class));
		}
		members.clear();
		return vos;
	}
	
	@Override
	public List<PunUserGroupVO> queryDirectManager(Long userId){
		return queryManager("queryDirectManager",userId);
	}
	
	/*
	 * 查找本组织上级领导名单，仅查找上一级的名单；，不作递归查找;
	 * @see org.szcloud.framework.unit.service.PunUserGroupService#queryParentManager(java.lang.Long)
	 */
	@Override
	public List<PunUserGroupVO> queryParentManager(Long userId){
		return queryManager("queryParentManager",userId);
	}
	
	private List<PunUserGroupVO> queryManager(String query, Long userId){	
		List<PunUserGroupVO> resultList = new ArrayList<PunUserGroupVO>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		List<PunUserGroup> list = queryChannel.queryResult(PunUserGroup.class, query, map);
		for (Object dd : list) {
			resultList.add(BeanUtils.getNewInstance(dd, PunUserGroupVO.class));			
		}
		return resultList;
	}
}