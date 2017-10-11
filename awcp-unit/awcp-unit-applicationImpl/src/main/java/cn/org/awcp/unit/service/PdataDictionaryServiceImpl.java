package cn.org.awcp.unit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.core.domain.QueryChannelService;
import cn.org.awcp.core.utils.BeanUtils;
import cn.org.awcp.unit.core.domain.PdataDictionary;
import cn.org.awcp.unit.service.PdataDictionaryService;
import cn.org.awcp.unit.vo.PdataDictionaryVO;

@Service(value="pdataDictionaryServiceImpl")
public class PdataDictionaryServiceImpl implements PdataDictionaryService{

	@Autowired
	private QueryChannelService queryChannel;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	//0为保存初始状态，1为逻辑删除
	private static final String DATA_DICT_SAVE = "0";
	private static final String DATA_DICT_LOGIC_DEL = "1";
	
	@Override
	public void addOrUpdate(PdataDictionaryVO vo) throws MRTException{
		PdataDictionary dataDict = BeanUtils.getNewInstance(vo, PdataDictionary.class);
		if (vo.getId() != null) {
			dataDict.setId(vo.getId());			
		}
		dataDict.setDictStatus(DATA_DICT_SAVE);
		dataDict.save();
	}

	@Override
	public String delete(Long id) {
		PdataDictionary dataDict = PdataDictionary.get(PdataDictionary.class, id);
		String code = dataDict.getCode();
		dataDict.remove();
		return code;
	}

	@Override
	public PdataDictionaryVO findById(Long id) throws MRTException{
		PdataDictionary dataDict = PdataDictionary.get(PdataDictionary.class, id);
		return BeanUtils.getNewInstance(dataDict, PdataDictionaryVO.class);
	}

	@Override
	public PdataDictionaryVO findByCode(String code){
		BaseExample baseExample = new BaseExample();
		String dc = code;
		if(!dc.endsWith(",")){
			dc += ",";
		}
		baseExample.createCriteria().andEqualTo("CODE", dc);
		List<PdataDictionaryVO> list = this.selectByExample(baseExample);
		if(list.isEmpty()){	
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public List<PdataDictionaryVO> findChildByParentCode(String code){
		PdataDictionaryVO parentVO = findByCode(code);
		if(parentVO == null)
			return null;
		Long level = parentVO.getLevel() + 1;
		BaseExample baseExample = new BaseExample();
		baseExample.createCriteria()
				   .andLike("CODE", parentVO.getCode() + "%")
				   .andEqualTo("LEVEL", level)
				   .andEqualTo("DICT_STATUS", new Long(0));
		return selectPagedByExample(baseExample, 1, Integer.MAX_VALUE," DATA_ORDER ASC");
	}
	
	@Override
	public List<PdataDictionaryVO> findAll() throws MRTException{
		List<PdataDictionary> result = PdataDictionary.findAll();
		List<PdataDictionaryVO> resultVO = new ArrayList<PdataDictionaryVO>();
		for (PdataDictionary dd : result) {
			if (!dd.getDictStatus().equals(DATA_DICT_LOGIC_DEL))
			resultVO.add(BeanUtils.getNewInstance(dd, PdataDictionaryVO.class));			
		}
		result.clear();
		return resultVO;
	}

	@Override
	public List<PdataDictionaryVO> queryResult(String queryStr,
			Map<String, Object> params) {
		List<PdataDictionary> result = queryChannel.queryResult(PdataDictionary.class,queryStr, params);
		List<PdataDictionaryVO> resultVO = new ArrayList<PdataDictionaryVO>();
		for (PdataDictionary dd : result) {
			if (!dd.getDictStatus().equals(DATA_DICT_LOGIC_DEL))
			resultVO.add(BeanUtils.getNewInstance(dd, PdataDictionaryVO.class));			
		}
		result.clear();
		return resultVO;
	}

	@Override
	public PageList<PdataDictionaryVO> queryPagedResult(String queryStr,
			Map<String, Object> params, int currentPage, int pageSize,String sortString) {
		PageList<PdataDictionary> result = queryChannel.queryPagedResult(PdataDictionary.class,
				queryStr, params, currentPage, pageSize, sortString);
		List<PdataDictionaryVO> tmp = new ArrayList<PdataDictionaryVO>();
		for (PdataDictionary dd : result) {
			if (!dd.getDictStatus().equals(DATA_DICT_LOGIC_DEL))
				tmp.add(BeanUtils.getNewInstance(dd, PdataDictionaryVO.class));			
		}
		PageList<PdataDictionaryVO> resultVO = new PageList<PdataDictionaryVO>(tmp,result.getPaginator());
		result.clear();
		return resultVO;
	}
	
	//根据id进行逻辑删除
	@Override
	public String logicDelete(Long id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("dictStatus", DATA_DICT_LOGIC_DEL);
			session.update(PdataDictionary.class.getName()+".logicDelete", params);
			session.commit();
		} finally{
			session.close();
		}
		return null;
	}
	
	//物理删除
	@Override
	public String deleteLikeCode(String code) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.delete(PdataDictionary.class.getName()+".deleteLikeCode", code);
			session.commit();
		} finally{
			session.close();
		}
		return null;
	}
	
	//逻辑删除
	@Override
	public String logicDeleteLikeCode(String code) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", code);
			params.put("dictStatus", DATA_DICT_LOGIC_DEL);
			session.update(PdataDictionary.class.getName()+".logicDeleteLikeCode", params);
			session.commit();
		} finally{
			session.close();
		}
		return null;
	}
	
	@Override
	public PageList<PdataDictionaryVO> selectPagedByExample(BaseExample baseExample, 
			int currentPage, int pageSize,String sortString) {
		PageList<PdataDictionary> result = queryChannel.selectPagedByExample(PdataDictionary.class,baseExample, 
				currentPage, pageSize, sortString);
		PageList<PdataDictionaryVO> resultVO = new PageList<PdataDictionaryVO>(result.getPaginator());
		for (PdataDictionary dd : result) {
			//过滤已作逻辑删除的结果
			if (!dd.getDictStatus().equals(DATA_DICT_LOGIC_DEL))
			resultVO.add(BeanUtils.getNewInstance(dd, PdataDictionaryVO.class));			
		}
		result.clear();
		return resultVO;
	}

	@Override
	public List<PdataDictionaryVO> selectByExample(BaseExample baseExample) {
		return selectPagedByExample(baseExample, 1, Integer.MAX_VALUE," ID DESC");
	}

}
