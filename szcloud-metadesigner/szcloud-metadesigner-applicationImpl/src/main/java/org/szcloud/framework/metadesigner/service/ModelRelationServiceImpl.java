package org.szcloud.framework.metadesigner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.metadesigner.application.ModelRelationService;
import org.szcloud.framework.metadesigner.core.domain.ModelRelation;
import org.szcloud.framework.metadesigner.vo.ModelRelationVO;

@Service(value="modelRelationServiceImpl")
public class ModelRelationServiceImpl implements ModelRelationService{
	
	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;
	
	public List<ModelRelationVO> findAll() {
		List<ModelRelation> model = ModelRelation.findAll();
		List<ModelRelationVO> mro = new ArrayList<ModelRelationVO>();
		for(ModelRelation mm:model){
			ModelRelationVO mmm = BeanUtils.getNewInstance(mm, ModelRelationVO.class);
			mro.add(mmm);
		}
		return mro;
	}
	
	public ModelRelationVO queryByItem(Long itemId) {
		Map<String,Object> obj = new HashMap<String, Object>();
		obj.put("itemId",itemId);
		ModelRelation mr = queryChannel.querySingleResult(ModelRelation.class, "queryByItem", obj);
		ModelRelationVO mro = BeanUtils.getNewInstance(mr, ModelRelationVO.class);
		return mro;
	}
	
	public List<ModelRelationVO> queryByModelId(Long modelId){
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("modelId", modelId);
		List<ModelRelation> list = queryChannel.queryResult(ModelRelation.class, "queryByModelId", obj);
		List<ModelRelationVO> ls = new ArrayList<ModelRelationVO>();
		for(ModelRelation m:list){
			ModelRelationVO mro = BeanUtils.getNewInstance(m, ModelRelationVO.class);
			ls.add(mro);
		}
		return ls;
	}
	
	public boolean save(ModelRelationVO vo) {
		try {
			ModelRelation mr = BeanUtils.getNewInstance(vo, ModelRelation.class);
			ModelRelation.getRepository().save(mr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean executeUpdate(ModelRelationVO vo,String queryStr) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", vo.getId());
		map.put("relationName", vo.getRelationName());
		map.put("relationType", vo.getRelationType());
		map.put("itemId", vo.getItemId());
		map.put("modelId", vo.getModelId());
		map.put("descript", vo.getDescript());
		try {
			ModelRelation.getRepository().executeUpdate(queryStr, map, ModelRelation.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean remove(ModelRelationVO vo) {
		try {
			ModelRelation mr=BeanUtils.getNewInstance(vo, ModelRelation.class);
			mr.remove();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
