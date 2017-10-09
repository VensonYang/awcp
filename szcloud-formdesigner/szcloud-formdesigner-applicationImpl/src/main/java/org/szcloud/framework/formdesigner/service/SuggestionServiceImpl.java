package org.szcloud.framework.formdesigner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.core.utils.Tools;
import org.szcloud.framework.core.utils.constants.SessionContants;
import org.szcloud.framework.formdesigner.application.service.SuggestionService;
import org.szcloud.framework.formdesigner.application.vo.SuggestionVO;
import org.szcloud.framework.formdesigner.core.domain.Suggestion;
import org.szcloud.framework.unit.vo.PunSystemVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service(value="suggestionServiceImpl")
public class SuggestionServiceImpl implements SuggestionService{

	@Autowired
	private QueryChannelService queryChannel;

	@Override	
	public SuggestionVO findById(String id) {
		SuggestionVO vo=BeanUtils.getNewInstance(Suggestion.get(id), SuggestionVO.class);
		return vo;
	}

	@Override
	public PageList<SuggestionVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {
		Object obj=Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if(obj instanceof PunSystemVO){
			PunSystemVO system = (PunSystemVO)obj;
			params.put("systemId", system.getSysId());
		}
		PageList<SuggestionVO> groups = queryChannel.queryPagedResult(SuggestionVO.class, queryStr, params, 
				currentPage, pageSize,sortString);
		List<SuggestionVO> tmp = new ArrayList<SuggestionVO>();
		for (SuggestionVO group : groups) {
			tmp.add(BeanUtils.getNewInstance(group, SuggestionVO.class));
		}
		PageList<SuggestionVO> vos = new PageList<SuggestionVO>(tmp,groups.getPaginator());
		groups.clear();
		return vos;
	}

	@Override
	public PageList<SuggestionVO> selectPagedByExample(BaseExample example,
			int currentPage, int pageSize, String sortString) {
		Object obj=Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if(obj instanceof SuggestionVO){
			PunSystemVO system = (PunSystemVO)obj;
			if(example.getOredCriteria().size()>0){
				example.getOredCriteria().get(0).andEqualTo("SYSTEM_ID", system.getSysId());
			}else{
				example.createCriteria().andEqualTo("SYSTEM_ID",system.getSysId());
			}
		}
		PageList<Suggestion> list = queryChannel.selectPagedByExample(Suggestion.class, example, 
				currentPage, pageSize, sortString);
		PageList<SuggestionVO> vos = new PageList<SuggestionVO>(list.getPaginator());
		for (Suggestion dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, SuggestionVO.class));
		}
		list.clear();
		return vos;
	}


	public String save(SuggestionVO vo) {
		Suggestion v = BeanUtils.getNewInstance(vo, Suggestion.class);
		String id = v.save();
		vo.setId(id);
		return id;
	}

	@Override
	public boolean delete(String[] ids) {
		try {
			for (String id : ids) {
				Suggestion au = BeanUtils.getNewInstance(Suggestion.get(id),Suggestion.class);
				au.remove();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<SuggestionVO> queryResult(String queryStr,Map<String, Object> params) throws MRTException {
		List<Suggestion> resources = queryChannel.queryResult(Suggestion.class, queryStr, params);
		List<SuggestionVO> vos = new ArrayList<SuggestionVO>();
		for (Suggestion resource : resources) {
			vos.add(BeanUtils.getNewInstance(resource, SuggestionVO.class));
		}
		resources.clear();
		return vos;
	}
}
