package cn.org.awcp.unit.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.core.domain.BaseExample;
import cn.org.awcp.unit.vo.SysDataSourceVO;

public interface SysSourceRelationService {
	
	SysDataSourceVO get(Long id);
	
	List<SysDataSourceVO> findAll();
	
	void remove(SysDataSourceVO vo);
	 
	SysDataSourceVO saveOrUpdate(SysDataSourceVO vo);
	 
	PageList<SysDataSourceVO> selectPagedByExample(BaseExample baseExample, int currentPage, int pageSize,String sortString) ;
	
}
