package org.szcloud.framework.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.formdesigner.application.vo.AuthorityGroupWorkFlowNodeVO;

public interface AuthorityGroupWorkFlowNodeService {

	public AuthorityGroupWorkFlowNodeVO findById(String id);
	
	public String save(AuthorityGroupWorkFlowNodeVO vo);
	
	public boolean delete(AuthorityGroupWorkFlowNodeVO id);
	
	public List<AuthorityGroupWorkFlowNodeVO> queryResult(String queryStr,Map<String, Object> params)throws MRTException;
}
