package cn.org.awcp.unit.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.unit.vo.PunSystemDictVO;

public interface PunSystemDictService {
	
	public void addOrUpdate(PunSystemDictVO vo);
	
	public PunSystemDictVO findById(Long id);
	
	public PunSystemDictVO findByCode(String code);
	
	public List<PunSystemDictVO> findLikeCode(String code);
	
	public List<PunSystemDictVO> findAll();
	
	public String delete(Long id);
	
	public List<PunSystemDictVO> queryResult(String queryStr,Map<String, Object> params);
	
	public PageList<PunSystemDictVO> queryPagedResult(String queryStr,Map<String, Object> params, 
			int currentPage, int pageSize,String sortString);
}
