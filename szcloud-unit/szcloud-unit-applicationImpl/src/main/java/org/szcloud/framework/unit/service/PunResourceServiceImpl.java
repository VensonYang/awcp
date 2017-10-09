package org.szcloud.framework.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.unit.core.domain.PunResource;
import org.szcloud.framework.unit.utils.IdentifierUtils;
import org.szcloud.framework.unit.vo.PunResourceVO;

@Service
public class PunResourceServiceImpl implements PunResourceService {

	@Autowired
	private QueryChannelService queryChannel;
	
	@Override
	public void addOrUpdate(PunResourceVO vo)  throws MRTException{
		PunResource resource = BeanUtils.getNewInstance(vo, PunResource.class);
		if(null != vo.getResourceId()){
			resource.setId(vo.getResourceId());
		}
		resource.save();
		vo.setResourceId(resource.getResourceId());
	}

	@Override
	public PunResourceVO findById(Long id)  throws MRTException{
		PunResource user = PunResource.get(PunResource.class, id);
		return BeanUtils.getNewInstance(user, PunResourceVO.class);
	}

	@Override
	public List<PunResourceVO> findAll()  throws MRTException{
		List<PunResource> result = PunResource.findAll();
		List<PunResourceVO> resultVo = new ArrayList<PunResourceVO>();
		for(PunResource mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunResourceVO.class));
		}
		result.clear();
		return resultVo; 
	}

	@Override
	public String delete(Long id)  throws MRTException{
		PunResource resource = PunResource.get(PunResource.class, id);
		resource.remove();
		return null;
	}

	public void removeByRelateResoAndType(String relateResoId, String type){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("relateResoId", relateResoId);
		params.put("type", type);
		queryChannel.excuteMethod(PunResource.class, "removeByRelateResoAndType", params);
	}
	
	@Override
	public List<PunResourceVO> queryResult(String queryStr,Map<String, Object> params)  throws MRTException{
		List<PunResource> resources = queryChannel.queryResult(PunResource.class, queryStr, params);
		List<PunResourceVO> vos = new ArrayList<PunResourceVO>();
		for (PunResource resource : resources) {
			vos.add(BeanUtils.getNewInstance(resource, PunResourceVO.class));
		}
		resources.clear();
		return vos;
	}	
	
	@Override
	public Map<String, PunResourceVO> queryButtonMap(String queryStr,Map<String, Object> params) {
		List<PunResource> resources = queryChannel.queryResult(PunResource.class, queryStr, params);
		Map<String, PunResourceVO> buttonMap = new HashMap<String, PunResourceVO>();
		for (PunResource punResource : resources) {
			PunResourceVO vo = BeanUtils.getNewInstance(punResource, PunResourceVO.class);
			buttonMap.put(punResource.getRelateResoId(), vo);
		}
		return buttonMap;
	}

	@Override
	public List<PunResourceVO> getPunResourceByIds(List<Long> resourceIds)  throws MRTException{
		Map<String, Object> params = new HashMap<String, Object>();
		String ids = IdentifierUtils.getLongIdStringForSql(resourceIds);
		params.put("ids", ids);
		return this.queryResult("getResourceListByIds", params);
	}
	
	public List<PunResourceVO> getResourceListByRelateIds(List<Long> relateResourceId, int resourceType)   throws MRTException{
		Map<String, Object> params = new HashMap<String, Object>();
		String ids = IdentifierUtils.getLongIdStringForSql(relateResourceId);
		params.put("ids", ids);
		params.put("resourceType", resourceType);
		return this.queryResult("getResourceListByRelateIds", params);
	}

	@Override
	public List<String> queryResoIds(String queryStr, Map<String, Object> params) {
		List<PunResource> resources = queryChannel.queryResult(PunResource.class, queryStr, params);
		List<String> results = new ArrayList<String>();
		for (PunResource punResource : resources) {
			results.add(punResource.getResourceId().toString());
		}
		return results;
	}
	
	public Long getResourceIdByRelateId(String relateId, String resourceType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("relateId", relateId);
		params.put("resourceType", resourceType);
		List<PunResourceVO> list = this.queryResult("getResourceIdByRelateId", params);
		if(list != null && list.size()>0){
			return ((PunResourceVO)list.get(0)).getResourceId();
		}
		return null;
	}
	
	public PunResourceVO getResourceByRelateId(String relateId, String resourceType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("relateId", relateId);
		params.put("resourceType", resourceType);
		List<PunResourceVO> list = this.queryResult("getResourceIdByRelateId", params);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
