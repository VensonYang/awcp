package org.szcloud.framework.formdesigner.application.service;

import java.util.List;
import java.util.Map;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.formdesigner.application.vo.PfmTemplateVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface PfmTemplateService {
	
	public List<PfmTemplateVO> findAll();
	
	public void remove(PfmTemplateVO vo);
	
	public void update(PfmTemplateVO vo,String queryStr);
	
	public List<PfmTemplateVO> queryResult(String queryStr,Map<String, Object> params);
	
	public PageList<PfmTemplateVO> selectPagedByExample(BaseExample baseExample,
			int currentPage, int pageSize,String sortString);
	
	public List<PfmTemplateVO> selectByExample(BaseExample baseExample);
		
	public void save(PfmTemplateVO vo);
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PfmTemplateVO> queryPagedResult(Map<String, Object> params, 
			int currentPage, int pageSize,String sortString);
	
	public PfmTemplateVO get(Long id);
	
}