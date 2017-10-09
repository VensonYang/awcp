package org.szcloud.framework.formdesigner.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.szcloud.framework.formdesigner.application.service.AuthorityGroupService;
import org.szcloud.framework.formdesigner.application.vo.AuthorityGroupVO;
import org.szcloud.framework.formdesigner.core.domain.AuthorityGroup;
import org.szcloud.framework.unit.vo.PunSystemVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service(value="authorityGroupServiceImpl")
public class AuthorityGroupServiceImpl implements AuthorityGroupService{

	@Autowired
	private QueryChannelService queryChannel;
		
	@Override
	public AuthorityGroupVO findById(String id) {
		AuthorityGroupVO vo=BeanUtils.getNewInstance(AuthorityGroup.get(id), AuthorityGroupVO.class);
		return vo;
	}
	
	@Override
	public List<AuthorityGroupVO> listByDynamicPageId(Long dynamicPageId) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dynamicPageId", dynamicPageId);
		BaseExample baseExample = new BaseExample();
		baseExample.createCriteria().andEqualTo("DYNAMICPAGE_ID", dynamicPageId);
		
		PageList<AuthorityGroupVO> list = selectPagedByExample(baseExample, 1, Integer.MAX_VALUE, null);
		return list;
	}
	
	@Override
	public PageList<AuthorityGroupVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString) {		
		Object obj=Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if(obj instanceof PunSystemVO){
			PunSystemVO system = (PunSystemVO)obj;
			params.put("systemId", system.getSysId());
		}		
		PageList<AuthorityGroupVO> groups = queryChannel.queryPagedResult(AuthorityGroupVO.class, queryStr, params, 
				currentPage, pageSize,sortString);
		List<AuthorityGroupVO> tmp = new ArrayList<AuthorityGroupVO>();
		for (AuthorityGroupVO group : groups) {
			tmp.add(BeanUtils.getNewInstance(group, AuthorityGroupVO.class));
		}
		PageList<AuthorityGroupVO> vos = new PageList<AuthorityGroupVO>(tmp,groups.getPaginator());
		groups.clear();
		return vos;
	}
	
	@Override
	public PageList<AuthorityGroupVO> selectPagedByExample(BaseExample example,
			int currentPage, int pageSize, String sortString) {
		Object obj = Tools.getObjectFromSession(SessionContants.TARGET_SYSTEM);
		if(obj instanceof AuthorityGroupVO){
			PunSystemVO system = (PunSystemVO)obj;
			if(example.getOredCriteria().size()>0){
				example.getOredCriteria().get(0).andEqualTo("SYSTEM_ID", system.getSysId());
			}else{
				example.createCriteria().andEqualTo("SYSTEM_ID",system.getSysId());
			}
		}	
		PageList<AuthorityGroup> list = queryChannel.selectPagedByExample(AuthorityGroup.class, example, 
				currentPage, pageSize, sortString);
		PageList<AuthorityGroupVO> vos = new PageList<AuthorityGroupVO>(list.getPaginator());
		for (AuthorityGroup dp : list) {
			vos.add(BeanUtils.getNewInstance(dp, AuthorityGroupVO.class));
		}
		list.clear();
		return vos;
	}
	
	public String save(AuthorityGroupVO vo) {
		AuthorityGroup v = BeanUtils.getNewInstance(vo, AuthorityGroup.class);
		String id = v.save();
		vo.setId(id);
		return id;
	}
	
	@Override
	public boolean delete(String[] ids) {		
		try {
			for (String id : ids) {
				AuthorityGroup au = BeanUtils.getNewInstance(AuthorityGroup.get(id),AuthorityGroup.class);
				au.remove();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<AuthorityGroupVO> queryResult(String queryStr,Map<String, Object> params) throws MRTException {		
		List<AuthorityGroupVO> resources = queryChannel.queryResult(AuthorityGroupVO.class, queryStr, params);
		List<AuthorityGroupVO> vos = new ArrayList<AuthorityGroupVO>();		
		for (AuthorityGroupVO resource : resources) {
			vos.add(BeanUtils.getNewInstance(resource, AuthorityGroupVO.class));
		}
		resources.clear();
		return vos;
	}
	
}
