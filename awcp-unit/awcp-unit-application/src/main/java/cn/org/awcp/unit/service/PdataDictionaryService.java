package cn.org.awcp.unit.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.unit.vo.PdataDictionaryVO;

public interface PdataDictionaryService {
	
	public void addOrUpdate(PdataDictionaryVO vo);
	
	public String delete(Long id);
	
	public String logicDelete(Long id);
		
	public String deleteLikeCode(String code);
	
	public String logicDeleteLikeCode(String code);	
	
	public PdataDictionaryVO findById(Long id);
	
	/**
	 * 根据code查找字典项；
	 */
	public PdataDictionaryVO findByCode(String code);
	
	/**
	 * 根据父亲code查找其子数据字典项；
	 */
	public List<PdataDictionaryVO> findChildByParentCode(String parentCode);
	
	public List<PdataDictionaryVO> findAll();
	
	public List<PdataDictionaryVO> queryResult(String queryStr,Map<String, Object> params);
	
	public PageList<PdataDictionaryVO> queryPagedResult(String queryStr,
			Map<String, Object> params, int currentPage, int pageSize,
			String sortString);
	
	public List<PdataDictionaryVO> selectByExample(BaseExample baseExample);
		
	public PageList<PdataDictionaryVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize,String sortString);

}
