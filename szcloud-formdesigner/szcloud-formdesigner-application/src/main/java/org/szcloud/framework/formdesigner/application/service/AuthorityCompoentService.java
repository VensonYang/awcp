package org.szcloud.framework.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.formdesigner.application.vo.AuthorityCompoentVO;

public interface AuthorityCompoentService {
	
	public AuthorityCompoentVO findById(String id);
	
	public AuthorityCompoentVO findByComponent(String componentId);
	
	public String save(AuthorityCompoentVO vo);
	
	public boolean update(String id);
	
	public List<AuthorityCompoentVO> queryResult(String queryStr,Map<String, Object> params)throws MRTException;
	
}
