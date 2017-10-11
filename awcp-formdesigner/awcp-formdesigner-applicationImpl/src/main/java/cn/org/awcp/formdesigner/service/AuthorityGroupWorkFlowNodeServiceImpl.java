package cn.org.awcp.formdesigner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.formdesigner.application.service.AuthorityGroupWorkFlowNodeService;
import cn.org.awcp.formdesigner.application.vo.AuthorityGroupWorkFlowNodeVO;
import cn.org.awcp.formdesigner.core.domain.AuthorityGroupWorkFlowNode;

@Service(value="authorityGroupWorkFlowNodeServiceImpl")
public class AuthorityGroupWorkFlowNodeServiceImpl implements AuthorityGroupWorkFlowNodeService{
	
	@Autowired
	private QueryChannelService queryChannel;
	
	@Override
	public AuthorityGroupWorkFlowNodeVO findById(String id) {	
		AuthorityGroupWorkFlowNodeVO vo=BeanUtils.getNewInstance(AuthorityGroupWorkFlowNode.get(id), 
				AuthorityGroupWorkFlowNodeVO.class);
		return vo;
	}

	@Override
	public String save(AuthorityGroupWorkFlowNodeVO vo) {		
		AuthorityGroupWorkFlowNode v = BeanUtils.getNewInstance(vo, AuthorityGroupWorkFlowNode.class);
		String id = v.save();
		vo.setId(id);
		return id;
	}

	@Override
	public boolean delete(AuthorityGroupWorkFlowNodeVO vo) {
		AuthorityGroupWorkFlowNode v = BeanUtils.getNewInstance(vo, AuthorityGroupWorkFlowNode.class);
		v.remove();
		return true;
	}

	@Override
	public List<AuthorityGroupWorkFlowNodeVO> queryResult(String queryStr,Map<String, Object> params) throws MRTException {	
		List<AuthorityGroupWorkFlowNode> resources = queryChannel.queryResult(AuthorityGroupWorkFlowNode.class, queryStr, params);
		List<AuthorityGroupWorkFlowNodeVO> vos = new ArrayList<AuthorityGroupWorkFlowNodeVO>();		
		for (AuthorityGroupWorkFlowNode resource : resources) {
			vos.add(BeanUtils.getNewInstance(resource, AuthorityGroupWorkFlowNodeVO.class));
		}
		resources.clear();
		return vos;		
	}

}
