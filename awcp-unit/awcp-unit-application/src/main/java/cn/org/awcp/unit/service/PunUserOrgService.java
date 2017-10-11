package cn.org.awcp.unit.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.org.awcp.unit.vo.PunUserOrgVO;

public interface PunUserOrgService {
	
	public List<PunUserOrgVO> findAll();
	
	public void remove(PunUserOrgVO vo);
	
	public void update(PunUserOrgVO vo);
		
	public void save(PunUserOrgVO vo);
	
	//参数：1.类名  2.mapper文件中对应的id  固定位queryList  3.根据条件来分页查询   4.当前页   5.取的记录他条数  6. 根据字段排序("name.asc")列子
	public PageList<PunUserOrgVO> selectPagedByExample(String queryStr, Map<String, Object> params, int currentPage, int pageSize,String sortString);
	
	public PunUserOrgVO get(Long id);
	
}