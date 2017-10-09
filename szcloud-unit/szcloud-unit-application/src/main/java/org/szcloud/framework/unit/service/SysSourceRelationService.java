package org.szcloud.framework.unit.service;

import java.util.List;

import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.unit.vo.SysDataSourceVO;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface SysSourceRelationService {
	
	SysDataSourceVO get(Long id);
	
	List<SysDataSourceVO> findAll();
	
	void remove(SysDataSourceVO vo);
	 
	SysDataSourceVO saveOrUpdate(SysDataSourceVO vo);
	 
	PageList<SysDataSourceVO> selectPagedByExample(BaseExample baseExample, int currentPage, int pageSize,String sortString) ;
	
}
