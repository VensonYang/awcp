package cn.org.awcp.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.formdesigner.application.vo.AuthorityCompoentVO;

public interface AuthorityCompoentService {
	
	public AuthorityCompoentVO findById(String id);
	
	public AuthorityCompoentVO findByComponent(String componentId);
	
	public String save(AuthorityCompoentVO vo);
	
	public boolean update(String id);
	
	public List<AuthorityCompoentVO> queryResult(String queryStr,Map<String, Object> params)throws MRTException;
	
}
