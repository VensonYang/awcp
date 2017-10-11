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

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.unit.core.domain.PunPosition;
import cn.org.awcp.unit.service.PunPositionService;
import cn.org.awcp.unit.service.WorkflowSyncService;
import cn.org.awcp.unit.vo.PunPositionVO;

@Service(value="punPositionServiceImpl")
@Transactional
public class PunPositionServiceImpl implements PunPositionService{

	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;

	@Autowired
	@Qualifier("workflowSyncServiceImpl")
	private WorkflowSyncService workflowSyncService;
	
	private static final Long WORKFLOW_POSITION_TYPE= new Long(5);
	
	public List<PunPositionVO> findAll() {
		List<PunPosition> list=PunPosition.findAll(PunPosition.class);
		List<PunPositionVO> ls=new ArrayList<PunPositionVO>();
		for(PunPosition mm:list){
			ls.add(BeanUtils.getNewInstance(mm, PunPositionVO.class));
		}
		return ls;
	}

	public void remove(PunPositionVO vo) {
		try {
			PunPosition mm=BeanUtils.getNewInstance(vo, PunPosition.class);
			removeRelations(mm);
			PunPosition.getRepository().remove(mm);
			workflowSyncService.removeGroup(mm.getPositionId(), WORKFLOW_POSITION_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除岗位与其它表的关联关系；
	 * auth:huangqr
	 * @param user
	 * @throws MRTException
	 */
	public void removeRelations(PunPosition position) throws MRTException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("positionId", position.getPositionId());
		queryChannel.excuteMethod(PunPosition.class, "removeUserGroup", params);
	}

	public void update(PunPositionVO vo,String queryStr) {
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("positionId",vo.getPositionId());
			map.put("name",vo.getName());
			map.put("shortName",vo.getShortName());
			map.put("groupId",vo.getGroupId());
			map.put("grade",vo.getGrade());
			PunPosition.getRepository().executeUpdate(queryStr, map, PunPosition.class);
			workflowSyncService.saveGroup(vo.getPositionId(), vo.getName(), WORKFLOW_POSITION_TYPE);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void save(PunPositionVO vo) {
		try {
			PunPosition mmc=BeanUtils.getNewInstance(vo, PunPosition.class);
			if(vo.getPositionId() != null ) {
				mmc.setId(vo.getPositionId());
			}
			PunPosition.getRepository().save(mmc);
			vo.setPositionId(mmc.getPositionId());
			workflowSyncService.saveGroup(mmc.getPositionId(), mmc.getName(), WORKFLOW_POSITION_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PunPositionVO> selectPagedByExample(String queryStr, Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {
		PageList<PunPositionVO> resultVO = new PageList<PunPositionVO>();
		PageList<PunPosition> result = queryChannel.queryPagedResult(PunPosition.class,queryStr,params, 
				currentPage, pageSize, sortString);
		for (Object dd : result) {
			resultVO.add(BeanUtils.getNewInstance(dd, PunPositionVO.class));			
		}
		result.clear();
		return resultVO;
	}
	
	public List<PunPositionVO> selectByExample(BaseExample example) throws MRTException{
		List<PunPosition> result = PunPosition.selectByExample(PunPosition.class, example);
		List<PunPositionVO> resultVo = new ArrayList<PunPositionVO>();
		for(PunPosition mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunPositionVO.class));
		}
		result.clear();
		return resultVo;
		
	}
	
	public PunPositionVO get(Long id) {
		try {
			PunPosition model =  PunPosition.get(PunPosition.class, id);
			return BeanUtils.getNewInstance(model, PunPositionVO.class);
		} catch (Exception e) {
			throw new RuntimeException("错误信息", e);
		}
	}

	@Override
	public List<PunPositionVO> queryResult(String queryStr,Map<String, Object> params) {
		List<PunPosition> result = queryChannel.queryResult(PunPosition.class, queryStr, params);
		List<PunPositionVO> resultVo = new ArrayList<PunPositionVO>();
		for(PunPosition mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunPositionVO.class));
		}
		result.clear();
		return resultVo;
	}
	
}