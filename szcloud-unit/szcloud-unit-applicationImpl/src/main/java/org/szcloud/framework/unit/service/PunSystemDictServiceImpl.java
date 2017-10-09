package org.szcloud.framework.unit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.unit.core.domain.PunSystemDict;
import org.szcloud.framework.unit.vo.PunSystemDictVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Transactional
@Service
public class PunSystemDictServiceImpl implements PunSystemDictService {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public void addOrUpdate(PunSystemDictVO vo) {
		PunSystemDict dict = BeanUtils.getNewInstance(vo, PunSystemDict.class);
		if(null != vo.getId()){
			dict.setId(vo.getId());
		}
		dict.save();
	}

	@Override
	public PunSystemDictVO findById(Long id) {
		PunSystemDict dict = PunSystemDict.get(PunSystemDict.class, id);
		return BeanUtils.getNewInstance(dict, PunSystemDictVO.class);
	}

	@Override
	public PunSystemDictVO findByCode(String code) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			PunSystemDict dict = (PunSystemDict) session.selectOne(PunSystemDict.class.getName() + ".getByCode", code);
			return BeanUtils.getNewInstance(dict, PunSystemDictVO.class);
		} finally {
			session.close();
		}
	}

	@Override
	public List<PunSystemDictVO> findLikeCode(String code) {
		BaseExample example = new BaseExample();
		example.or(example.createCriteria().andLike("code", code));
		List<PunSystemDict> result =  PunSystemDict.selectByExample(PunSystemDict.class, example);	
		List<PunSystemDictVO> resultVo = new ArrayList<PunSystemDictVO>();
		for(PunSystemDict mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunSystemDictVO.class));
		}
		result.clear();
		return resultVo; 
	}

	@Override
	public List<PunSystemDictVO> findAll() {
		List<PunSystemDict> result =  PunSystemDict.findAll(PunSystemDict.class);	
		List<PunSystemDictVO> resultVo = new ArrayList<PunSystemDictVO>();
		for(PunSystemDict mm : result){
			resultVo.add(BeanUtils.getNewInstance(mm, PunSystemDictVO.class));
		}
		result.clear();
		return resultVo;
	}

	@Override
	public String delete(Long id) {
		return null;
	}

	@Override
	public List<PunSystemDictVO> queryResult(String queryStr,
			Map<String, Object> params) {
		return null;
	}

	@Override
	public PageList<PunSystemDictVO> queryPagedResult(String queryStr,
			Map<String, Object> params, int currentPage, int pageSize,
			String sortString) {
		return null;
	}

}
