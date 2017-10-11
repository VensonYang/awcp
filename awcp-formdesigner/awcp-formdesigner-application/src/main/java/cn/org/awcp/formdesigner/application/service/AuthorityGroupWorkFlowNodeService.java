package cn.org.awcp.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.formdesigner.application.vo.AuthorityGroupWorkFlowNodeVO;

public interface AuthorityGroupWorkFlowNodeService {

	public AuthorityGroupWorkFlowNodeVO findById(String id);
	
	public String save(AuthorityGroupWorkFlowNodeVO vo);
	
	public boolean delete(AuthorityGroupWorkFlowNodeVO id);
	
	public List<AuthorityGroupWorkFlowNodeVO> queryResult(String queryStr,Map<String, Object> params)throws MRTException;
}
