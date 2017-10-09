package org.szcloud.framework.formdesigner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.formdesigner.application.service.AuthorityCompoentService;
import org.szcloud.framework.formdesigner.application.vo.AuthorityCompoentVO;
import org.szcloud.framework.formdesigner.core.domain.AuthorityCompoent;

@Service(value="authorityCompoentServiceImpl")
public class AuthorityCompoentServiceImpl implements AuthorityCompoentService{

	@SuppressWarnings("unused")
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Autowired
	private QueryChannelService queryChannel;

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public AuthorityCompoentVO findById(String id) {
		AuthorityCompoentVO vo=BeanUtils.getNewInstance(AuthorityCompoent.get(id), AuthorityCompoentVO.class);
		return vo;
	}

	public AuthorityCompoentVO findByComponent(String componentId){

		AuthorityCompoentVO vo=BeanUtils.getNewInstance(AuthorityCompoent.getByComponent(componentId), AuthorityCompoentVO.class);
		return vo;
	}

	@Override
	public String save(AuthorityCompoentVO vo) {
		AuthorityCompoent v = BeanUtils.getNewInstance(vo, AuthorityCompoent.class);
		String id = v.save();
		vo.setId(id);
		return id;
	}

	@Override
	public boolean update(String id) {
		return false;
	}

	@Override
	public List<AuthorityCompoentVO> queryResult(String queryStr,
			Map<String, Object> params) throws MRTException{
		List<AuthorityCompoent> resources = queryChannel.queryResult(
				AuthorityCompoent.class, queryStr, params);
		List<AuthorityCompoentVO> vos = new ArrayList<AuthorityCompoentVO>();

		for (AuthorityCompoent resource : resources) {
			vos.add(BeanUtils.getNewInstance(resource, AuthorityCompoentVO.class));
		}
		resources.clear();
		return vos;
	}

}
