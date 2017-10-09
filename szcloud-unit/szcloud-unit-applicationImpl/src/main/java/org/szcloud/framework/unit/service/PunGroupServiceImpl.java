package org.szcloud.framework.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.Criteria;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.unit.core.domain.PunGroup;
import org.szcloud.framework.unit.utils.PunGroupUtils;
import org.szcloud.framework.unit.vo.PunGroupVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Transactional
@Service
public class PunGroupServiceImpl implements PunGroupService {

	@Autowired
	private QueryChannelService queryChannel;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	@Qualifier("workflowSyncServiceImpl")
	private WorkflowSyncService workflowSyncService;

	/**
	 * 保存或修改
	 */
	public void addOrUpdate(PunGroupVO vo) throws MRTException {
		PunGroup group = BeanUtils.getNewInstance(vo, PunGroup.class);
		if (null != vo.getGroupId()) {
			group.setId(vo.getGroupId());
		}
		group.save();
		vo.setGroupId(group.getGroupId());
		Long wkGroupType = PunGroupUtils.getWorkflowGroupType(group.getGroupType());
		workflowSyncService.saveGroup(group.getGroupId(), group.getGroupChName(), wkGroupType);
		Long parentGroupId = group.getParentGroupId();
		if (parentGroupId == null || parentGroupId.longValue() == 0) {
			parentGroupId = null;
		}
		Long parentGroupType = new Long(2);// 默认为公司
		if (parentGroupId != null) {
			PunGroupVO pvo = findById(parentGroupId);
			if (pvo != null)
				parentGroupType = PunGroupUtils.getWorkflowGroupType(pvo.getGroupType());
		}
		// 创建Group之间的关系；
		if (parentGroupId != null) {
			workflowSyncService.createRelation(parentGroupId, parentGroupType, group.getGroupId(), wkGroupType,
					new Long(1), false);
		}
	}

	public PunGroupVO findById(Long id) throws MRTException {
		PunGroup user = PunGroup.get(PunGroup.class, id);
		return BeanUtils.getNewInstance(user, PunGroupVO.class);
	}

	public List<PunGroupVO> findAll() throws MRTException {
		List<PunGroup> result = PunGroup.findAll();
		List<PunGroupVO> resultVo = new ArrayList<PunGroupVO>();
		for (PunGroup mm : result) {
			resultVo.add(BeanUtils.getNewInstance(mm, PunGroupVO.class));
		}
		result.clear();
		return resultVo;
	}

	/**
	 * 根据ID删除
	 */
	public String delete(Long id) throws MRTException {
		PunGroup group = PunGroup.get(PunGroup.class, id);
		String groupName = group.getGroupChName();
		removeRelations(group);
		group.delete();
		Long wkGroupType = PunGroupUtils.getWorkflowGroupType(group.getGroupType());
		workflowSyncService.removeGroup(id, wkGroupType);
		return groupName;
	}

	/**
	 * 删除group与其它表的关联关系； auth:huangqr
	 * 
	 * @param group
	 * @throws MRTException
	 */
	private void removeRelations(PunGroup group) throws MRTException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", group.getGroupId());
		queryChannel.excuteMethod(PunGroup.class, "removeUserGroupByPosition", params);
		queryChannel.excuteMethod(PunGroup.class, "removePosition", params);
		queryChannel.excuteMethod(PunGroup.class, "removeUserGroupByGroup", params);
		queryChannel.excuteMethod(PunGroup.class, "removeGroupSys", params);
		String pid = group.getPid();
		if (pid == null) {
			pid = "";
		}
		pid = pid + group.getGroupId().toString() + ",";
		List<PunGroupVO> children = getGroupListByPid(pid);
		// 删除子结点；
		for (int i = 0; i < children.size(); i++) {
			delete(children.get(i).getGroupId());
		}
	}

	/**
	 * 
	 * @Title: cascadeDelete @Description: 级联删除 @author ljw @param @param
	 * pid @param @return @param @throws MRTException @return String @throws
	 */
	public void cascadeDelete(String pid) throws MRTException {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.delete(PunGroup.class.getName() + ".likeBatchDelete", pid);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @Title: queryResult @Description: 根据查询条件获取组信息 @author ljw @param @param
	 * params @param @return @param @throws MRTException @return
	 * List<PunGroupVO> @throws
	 */
	public List<PunGroupVO> queryResult(String queryStr, Map<String, Object> params) throws MRTException {
		List<PunGroup> groups = queryChannel.queryResult(PunGroup.class, queryStr, params);
		List<PunGroupVO> vos = new ArrayList<PunGroupVO>();
		for (PunGroup group : groups) {
			vos.add(BeanUtils.getNewInstance(group, PunGroupVO.class));
		}
		groups.clear();
		return vos;
	}

	public PunGroupVO getRootGroupByOrgCode(String orgCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgCode", orgCode);
		params.put("pid", "0");
		List<PunGroupVO> groupList = queryResult("eqQueryList", params);
		if (!groupList.isEmpty())
			return groupList.get(0);
		return null;

	}

	/**
	 * 
	 * @Title: queryResult @Description: 分页查询 @author ljw @param @param
	 * queryStr @param @param params @param @param currentPage @param @param
	 * pageSize @param @param sortString @param @return @return
	 * PageList<T> @throws
	 */
	public PageList<PunGroupVO> queryPagedResult(String queryStr, Map<String, Object> params, int currentPage,
			int pageSize, String sortString) {
		PageList<PunGroup> groups = queryChannel.queryPagedResult(PunGroup.class, queryStr, params, currentPage,
				pageSize, sortString);
		List<PunGroupVO> tmp = new ArrayList<PunGroupVO>();
		for (PunGroup group : groups) {
			tmp.add(BeanUtils.getNewInstance(group, PunGroupVO.class));
		}
		PageList<PunGroupVO> vos = new PageList<PunGroupVO>(tmp, groups.getPaginator());
		groups.clear();
		return vos;
	}

	public List<PunGroupVO> getGroupListByPid(String pid) throws MRTException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (!pid.endsWith(",")) {
			pid = pid + ",";
		}
		pid = pid + "%";
		params.put("pid", pid);
		return this.queryResult("queryByPID", params);
	}

	public List<PunGroupVO> getGroupListByPids(String pid) throws MRTException {
		Map<String, Object> params = new HashMap<String, Object>();
		pid = "%" + pid + "%";
		params.put("pid", pid);
		return this.queryResult("queryByPID", params);
	}

	@Override
	public PageList<PunGroupVO> selectPagedByExample(BaseExample baseExample, int currentPage, int pageSize,String sortString) {
		PageList<PunGroup> list = queryChannel.selectPagedByExample(PunGroup.class, baseExample, currentPage, pageSize,sortString);
		PageList<PunGroupVO> vos = new PageList<PunGroupVO>(list.getPaginator());
		for (PunGroup dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, PunGroupVO.class));
		}
		list.clear();
		return vos;
	}

	@Override
	public PageList<PunGroupVO> selectPagedByExample2(BaseExample baseExample, int currentPage, int pageSize,String sortString) {
		PageList<PunGroup> list = queryChannel.selectPagedByExample("queryCountByExampleWithUser",
				"selectByExampleWithUser", PunGroup.class, baseExample, currentPage, pageSize, sortString);
		PageList<PunGroupVO> vos = new PageList<PunGroupVO>(list.getPaginator());
		for (PunGroup dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, PunGroupVO.class));
		}
		list.clear();
		return vos;
	}

	public PageList<PunGroupVO> queryGroupByUserId(Long userId, int currentPage, int pageSize, String sortString) {
		if (userId == null) {
			return null;
		}
		BaseExample baseExample = new BaseExample();
		Criteria criteria = baseExample.createCriteria();
		criteria.andEqualTo("u.USER_ID", userId);
		PageList<PunGroup> list = queryChannel.selectPagedByExample("queryCountByExampleWithUser",
				"selectByExampleWithUser", PunGroup.class, baseExample, currentPage, pageSize, sortString);
		PageList<PunGroupVO> vos = new PageList<PunGroupVO>(list.getPaginator());
		for (PunGroup dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, PunGroupVO.class));
		}
		list.clear();
		return vos;
	}

}
